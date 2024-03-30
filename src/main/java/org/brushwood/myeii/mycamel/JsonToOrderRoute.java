package org.brushwood.myeii.mycamel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class JsonToOrderRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file:///tmp/mycamel?fileName=data.json&noop=true&delay=15m").routeId("bar-crawl-route1")
                .unmarshal().json(JsonLibrary.Jackson, Order.class)
                .process(exchange -> {
                    Order order = exchange.getIn().getBody(Order.class);
                    if (order != null && order.getItems() != null) {
                        for (OrderItem item : order.getItems()) {
                            item.setOrder(order); // Setting the order for each item
                        }
                    }
                })
                .to("jpa:Order");

    }
}
