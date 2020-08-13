package com.invoice;

import com.invoice.entities.InvoiceEntity;
import com.invoice.rest.models.Analytics;
import com.invoice.rest.models.Invoice;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {
    private static final String INVOICE_JSON = "{\n" +
            "    \"id\": 2,\n" +
            "    \"customer\": {\n" +
            "        \"id\": 6,\n" +
            "        \"firstName\": \"Alice\",\n" +
            "        \"lastName\": \"Mitchell\",\n" +
            "        \"emailId\": \"abc@gmail.com\",\n" +
            "        \"addresses\": [\n" +
            "            {\n" +
            "                \"street\": \"1234 XYZ Pl\",\n" +
            "                \"city\": \"Redwood\",\n" +
            "                \"state\": \"CA\",\n" +
            "                \"country\": \"USA\",\n" +
            "                \"zipCode\": 94332\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    \"lineItems\": [\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"product\": {\n" +
            "                \"id\": 1,\n" +
            "                \"name\": \"ProductA\",\n" +
            "                \"price\": 100\n" +
            "            },\n" +
            "            \"quantity\": 2,\n" +
            "            \"amount\": 200\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"product\": {\n" +
            "                \"id\": 2,\n" +
            "                \"name\": \"ProductB\",\n" +
            "                \"price\": 150\n" +
            "            },\n" +
            "            \"quantity\": 3,\n" +
            "            \"amount\": 450\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private static final String ANALYTICS_JSON = "{\n"+
            "    \"productAnalytics\": [\n"+
            "        {\n"+
            "            \"productId\": 1,\n"+
            "            \"productName\": \"ProductA\",\n"+
            "            \"totalQuantity\": 6,\n"+
            "            \"totalAmount\": 600\n"+
            "        },\n"+
            "        {\n"+
            "            \"productId\": 2,\n"+
            "            \"productName\": \"ProductB\",\n"+
            "            \"totalQuantity\": 9,\n"+
            "            \"totalAmount\": 1350\n"+
            "        }\n"+
            "    ],\n"+
            "    \"invoiceAnalytics\": [\n"+
            "        {\n"+
            "            \"invoiceId\": 2,\n"+
            "            \"totalAmount\": 650\n"+
            "        },\n"+
            "        {\n"+
            "            \"invoiceId\": 3,\n"+
            "            \"totalAmount\": 650\n"+
            "        },\n"+
            "        {\n"+
            "            \"invoiceId\": 8,\n"+
            "            \"totalAmount\": 650\n"+
            "        }\n"+
            "    ]\n"+
            "}";

    public static Invoice getMockedInvoice(){
        return JsonUtil.convertFromJson(INVOICE_JSON, Invoice.class);
    }
    public static InvoiceEntity getMockedInvoiceEntity(){
        return JsonUtil.convertFromJson(INVOICE_JSON, InvoiceEntity.class);
    }

    public static List<Invoice> getMockedInvoiceList(){
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(getMockedInvoice());
        return invoiceList;
    }

    public static List<InvoiceEntity> getMockedInvoiceEntityList(){
        List<InvoiceEntity> invoiceList = new ArrayList<>();
        invoiceList.add(getMockedInvoiceEntity());
        return invoiceList;
    }

    public static Analytics getMockedAnalytics(){
        return JsonUtil.convertFromJson(ANALYTICS_JSON, Analytics.class);
    }

    public static List getProductAnalyticsResult() {
        Object[] products = new Object[3];
        products[0] = getMockedInvoiceEntity().getLineItems().get(0).getProduct();
        products[1] = 6l;
        products[2] = 650.00;
        List result = new ArrayList();
        result.add(products);
        return result;
    }

    public static List getInvoiceAnalyticsResult() {
        Object[] products = new Object[2];
        products[0] = getMockedInvoiceEntity();
        products[1] = 650.00;
        List result = new ArrayList();
        result.add(products);
        result.add(products);
        return result;
    }
}
