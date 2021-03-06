package introsde.assignment.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class JaxWsLoggingHandler implements SOAPHandler<SOAPMessageContext> {

	private String filename;
	private int requestNum = 0;
	private FileOutputStream fos;
	
	public JaxWsLoggingHandler(FileOutputStream fos) {
		this.fos = fos;
	}
	
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		
		SOAPMessage message = context.getMessage();
		//message.
        boolean isOutboundMessage = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (isOutboundMessage) {
            System.out.println("OUTBOUND MESSAGE\n");
            try {
				fos.write("\nXML\n".getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
        } else {
            System.out.println("INBOUND MESSAGE\n");
            try {
				fos.write("\nXML\n".getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        try {
            message.writeTo(fos);
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		SOAPMessage message = context.getMessage();
        try {
            message.writeTo(System.out);
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}
	
	public void setRequestNum(int requestNum) {
		this.requestNum = requestNum;
	}

}
