package com.acme.cxf;
import com.acme.cxf.impl.HelloServiceImpl;
import com.acme.cxf.security.UTPasswordCallback;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SecureServer {
    public static void main(String[] args) throws Exception {

        Map<String,Object> props = new HashMap<>();
        props.put("action", "UsernameToken");
        props.put("passwordType", "PasswordText");
        props.put("passwordCallbackRef", new UTPasswordCallback(Map.of("student","secret123")));

        WSS4JInInterceptor wssIn = new WSS4JInInterceptor(props);

        String address = "http://localhost:8080/services/hello-secure";
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(HelloServiceImpl.class);
        factory.setAddress(address);
        Server server = factory.create();

        server.getEndpoint().getInInterceptors().add(wssIn);

        System.out.println("🔐 Secure CXF server started");
        System.out.println("✅ Secure WSDL: " + address + "?wsdl");
        System.out.println("User: student | Pass: secret123");
        System.out.println("Press Ctrl+C to stop.");

        new CountDownLatch(1).await();
    }
}
