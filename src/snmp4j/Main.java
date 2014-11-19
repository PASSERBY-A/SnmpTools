package snmp4j;

import java.io.IOException;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class Main extends Thread implements CommandResponder  {

	public static void main(String[] args) throws Exception {
		Main m = new Main();

		m.start();

	}

	@Override
	public void run() {

		try {
			TransportMapping transport;
			// 创建SNMP
			Address listenAddress = GenericAddress.parse("udp:10.208.100.19/161");
			;

			if (listenAddress instanceof UdpAddress) {
				transport = new DefaultUdpTransportMapping(
						(UdpAddress) listenAddress);
			} else {
				transport = new DefaultTcpTransportMapping(
						(TcpAddress) listenAddress);
			}

			Snmp snmp = new Snmp(transport);

			// 处理V1,V2,V3
			snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
			snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
			snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
			USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
					MPv3.createLocalEngineID()), 0);
			SecurityModels.getInstance().addSecurityModel(usm);

			// 注册命令响应监听器 
			 snmp.addCommandResponder(this);
			try {
				snmp.listen();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
	}

	public void processPdu(CommandResponderEvent arg0) {
		
		System.out.println("sssssssssssss!!!!!!!!!!");
		
	}
 
	
	
	 
}
