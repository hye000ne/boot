package com.sinse.busankidscafe.parser;

import com.sinse.busankidscafe.model.KidsCafe;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

// SAX 파서 핸들러
public class KidsCafeHandler extends DefaultHandler {
    private List<KidsCafe> kidsCafes = new ArrayList<KidsCafe>();
    private KidsCafe kidsCafe;
    private StringBuilder data;

    public List<KidsCafe> getKidsCafes() { return  kidsCafes; }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("item")) kidsCafe = new KidsCafe();
        data = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(kidsCafe != null) {
            switch(qName){
                case "gugun" : kidsCafe.setGugun(data.toString().trim()); break;
                case "cafe_nm" : kidsCafe.setCafe_nm(data.toString().trim()); break;
                case "road_nm" : kidsCafe.setRoad_nm(data.toString().trim()); break;
                case "tel_no" : kidsCafe.setTel_no(data.toString().trim()); break;
                case "lat" : kidsCafe.setLat(Double.parseDouble(data.toString().trim())); break;
                case "lng" : kidsCafe.setLng(Double.parseDouble(data.toString().trim())); break;
                case "data_date" : kidsCafe.setData_date(data.toString().trim()); break;
                case "item" : kidsCafes.add(kidsCafe); break;
            }
        }
    }
}
