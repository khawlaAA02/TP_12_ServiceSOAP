package com.acme.cxf;

import com.acme.cxf.impl.HelloServiceImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import java.util.concurrent.CountDownLatch;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        String address = "http://localhost:8081/services/hello";

        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(HelloServiceImpl.class);
        factory.setAddress(address);
        factory.create();

        System.out.println("✅ CXF server started");
        System.out.println("WSDL: " + address + "?wsdl");
        System.out.println("Press Ctrl+C to stop.");

        // Empêche l'arrêt immédiat du process
        new CountDownLatch(1).await();
    }
}
