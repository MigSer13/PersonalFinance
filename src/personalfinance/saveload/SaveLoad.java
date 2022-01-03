package personalfinance.saveload;

import personalfinance.settings.Settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class SaveLoad {
    public static void save(SaveData saveData){
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Wrapper wrapper = new Wrapper();
            wrapper.setAccounts(saveData.getAccounts());
            wrapper.setArticles(saveData.getArticles());
            wrapper.setTransactions(saveData.getTransactions());
            wrapper.setTransfers(saveData.getTransfers());
            wrapper.setCurrencies(saveData.getCurrencies());
            marshaller.marshal(wrapper, Settings.getFileSave());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void load(SaveData saveData){
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(Wrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Wrapper wrapper = (Wrapper) unmarshaller.unmarshal(Settings.getFileSave());
            saveData.setArticles(wrapper.getArticles());
            saveData.setAccounts(wrapper.getAccounts());
            saveData.setTransactions(wrapper.getTransactions());
            saveData.setTransfers(wrapper.getTransfers());
            saveData.setCurrencies(wrapper.getCurrencies());
        } catch (JAXBException e) {
            System.out.println("Файл не существует");
        }
    }
}
