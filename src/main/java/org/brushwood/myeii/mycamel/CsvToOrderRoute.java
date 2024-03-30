package org.brushwood.myeii.mycamel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CsvToOrderRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        CsvDataFormat csv = new CsvDataFormat();

        csv.setDelimiter(','); // Comma
        csv.setQuoteDisabled(true); // Otherwise single quotes will be doubled.

        onException(IOException.class)
                .handled(true)
                .log("IOException exception occurred: ${exception.message}");

        onException(Exception.class)
                .handled(true)
                .log("General exception occurred: ${exception.message}");

        from("file:///tmp/mycamel?fileName=data.csv&noop=true&delay=15m")

                .unmarshal(csv)
                .convertBodyTo(List.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        List<List<String>> data = (List<List<String>>) exchange.getIn().getBody();
                        for (List<String> line : data) {
                            // Checks if column two contains text STANDARD
                            // and alters its value to DELUXE.
                            if ("STANDARD".equals(line.get(1))) {
                                System.out.println("Original:" + line);
                                line.set(1, "DELUXE");
                                System.out.println("After: " + line);
                            }
                        }
                    }


                }).marshal(csv).to("file:///tmp/mycamel?fileName=out.csv")
                .log("done.").end();


    }
}
