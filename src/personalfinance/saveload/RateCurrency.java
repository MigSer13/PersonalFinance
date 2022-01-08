package personalfinance.saveload;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import personalfinance.model.Currency;

import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RateCurrency {

    public static HashMap<String, Double> getRate(Currency base) throws Exception {
        HashMap<String, NodeList> result = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String url = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + dateFormat.format(new Date());
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = documentBuilderFactory.newDocumentBuilder().parse(new URL(url).openStream());
        NodeList nodeList = document.getElementsByTagName("Valute");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            NodeList childNodes = item.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeName().equals("CharCode")){
                    result.put(childNodes.item(j).getTextContent(), childNodes);
                }
            }
        }

        HashMap<String, Double> rates = new HashMap<>();
        for (Map.Entry<String, NodeList> entry : result.entrySet()) {
            NodeList temp = entry.getValue();
            double value = 0;
            int nominal = 0;
            for (int i = 0; i < temp.getLength(); i++) {
                if(temp.item(i).getNodeName().equals("Value")){
                    value = Double.parseDouble(temp.item(i).getTextContent().replace(",", "."));
                }else if(temp.item(i).getNodeName().equals("Nominal")){
                    nominal = Integer.parseInt(temp.item(i).getTextContent().replace(",", "."));
                }
            }
            double amount = value / nominal;
            rates.put(entry.getKey(), ((double)Math.round(amount * 10_000)) / 10_000 );
        }
        rates.put("RUB", 1d);
        double div = rates.get(base.getCode());

        for (Map.Entry<String, Double> entry : rates.entrySet()) {
            entry.setValue(entry.getValue() / div);
        }
        return rates;
    }
}
