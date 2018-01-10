package com.froy.bwt.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.froy.bwt.bwt.generated.BorderWaitTime;

public class MarshallingBWTData {
	
	public BorderWaitTime unmarshall(String data) throws UnsupportedEncodingException{
 
			System.out.println("Unmarshalling") ;
			
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(BorderWaitTime.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8.name()));
				BorderWaitTime customer = (BorderWaitTime) jaxbUnmarshaller.unmarshal(stream);
				System.out.println(customer.toString());
				return customer;
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	return null;
	}
}
