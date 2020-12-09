package oil_server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;



import java.util.*; 
import java.text.*;
 
 
//ServerSocket (Server) : 데이터 전달 (계속 대기)
@SuppressWarnings("unused")

class Thread1 extends Thread {
   
   private void start1() throws Exception{
       Connection c = null;
        Statement stmt = null;
        String sql = null;
       
        
        String tradedt = null;
        String prodcd = null;
        String prodnm = null;
        String price = null;
        String diff = null;
        
        URL url = new URL("http://www.opinet.co.kr/api/avgAllPrice.do?out=xml&code=F061160929");
        URLConnection connection = url.openConnection();

        Document doc = parseXML(connection.getInputStream());
        doc.getDocumentElement().normalize();
        NodeList descNodes = doc.getElementsByTagName("OIL");
        
        System.out.println(descNodes.getLength());
        
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection("jdbc:postgresql://202.30.30.121:5433/postgres","postgres", "postgres");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");
 
        for(int i=0; i<descNodes.getLength();i++){
 
            for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){ //첫번째 자식을 시작으로 마지막까지 다음 형제를 실행
 
               try {
                      
                      if(node.getNodeName().equals("TRADE_DT")){
                        //System.out.println(node.getTextContent());
                        tradedt = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("PRODCD")){
                        //System.out.println(node.getTextContent());
                        prodcd = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("PRODNM")){
                        //System.out.println(node.getTextContent());
                        prodnm = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("PRICE")){
                         //System.out.println(node.getTextContent());
                         price = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("DIFF")){
                         //System.out.println(node.getTextContent());
                         diff = node.getTextContent().toString();
                      }
                    
               } catch (Exception e) {
                      e.printStackTrace();
                      System.err.println(e.getClass().getName()+": "+e.getMessage());
                      System.exit(0);
                }
            }
            
            
            //try
            //{
               //System.out.println(tradedt);
               stmt = c.createStatement();
                sql = "UPDATE oilavgallprice SET "
                   + "tradedt=" + "'" + tradedt + "'" + ", "
                   + "price=" + "'" + price + "'" + ", "
                   + "diff=" + "'" + diff + "' " 
                   + "WHERE " + "prodcd=" + "'" + prodcd + "' "
                   + "and " + "prodnm=" + "'" + prodnm + "';";
                stmt.executeUpdate(sql);
                
                
            //} catch (Exception e) {
                
            //}
        }
        System.out.println("Records update successfully");
        stmt.close();
        c.commit();
        c.close();
        
    }
    
    private void start2() throws Exception{
       Connection c = null;
        Statement stmt = null;
        String sql = null;
       
        
        String sidocd = null;
        String sidonm = null;
        String prodcd = null;
        String price = null;
        String diff = null;
        
        URL url = new URL("http://www.opinet.co.kr/api/avgSidoPrice.do?out=xml&code=F061160929");
        URLConnection connection = url.openConnection();

        Document doc = parseXML(connection.getInputStream());
        doc.getDocumentElement().normalize();
        NodeList descNodes = doc.getElementsByTagName("OIL");
        
        System.out.println(descNodes.getLength());
        
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection("jdbc:postgresql://202.30.30.121:5433/postgres","postgres", "postgres");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");
 
        for(int i=0; i<descNodes.getLength();i++){
 
            for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){ //첫번째 자식을 시작으로 마지막까지 다음 형제를 실행
 
               try {
                      
                      if(node.getNodeName().equals("SIDOCD")){
                        //System.out.println(node.getTextContent());
                         sidocd = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("SIDONM")){
                        //System.out.println(node.getTextContent());
                         sidonm = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("PRODCD")){
                        //System.out.println(node.getTextContent());
                         prodcd = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("PRICE")){
                         //System.out.println(node.getTextContent());
                         price = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("DIFF")){
                         //System.out.println(node.getTextContent());
                         diff = node.getTextContent().toString();
                      }
                    
               } catch (Exception e) {
                      e.printStackTrace();
                      System.err.println(e.getClass().getName()+": "+e.getMessage());
                      System.exit(0);
                }
            }
            
            
            //try
            //{
               //System.out.println(tradedt);
            stmt = c.createStatement();
            sql = "UPDATE oilavgsidoprice SET "
               + "sidocd=" + "'" + sidocd + "'" + ", "
               + "price=" + "'" + price + "'" + ", "
               + "diff=" + "'" + diff + "' " 
               + "WHERE " + "sidonm=" + "'" + sidonm + "' "
               + "and " + "prodcd=" + "'" + prodcd + "';";
            stmt.executeUpdate(sql);
                
                
            //} catch (Exception e) {
                
            //}
        }
        System.out.println("Records update successfully");
        stmt.close();
        c.commit();
        c.close();
        
    }
    
    private void start3() throws Exception{
       Connection c = null;
        Statement stmt = null;
        String sql = null;
       
        
        String SIGUNCD = null;
        String SIGUNNM = null;
        String prodcd = null;
        String price = null;
        String diff = null;
        
        URL url = new URL("http://www.opinet.co.kr/api/avgSigunPrice.do?out=xml&sido=01&code=F061160929");
        URLConnection connection = url.openConnection();

        Document doc = parseXML(connection.getInputStream());
        doc.getDocumentElement().normalize();
        NodeList descNodes = doc.getElementsByTagName("OIL");
        
        System.out.println(descNodes.getLength());
        
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection("jdbc:postgresql://202.30.30.121:5433/postgres","postgres", "postgres");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");
 
        for(int i=0; i<descNodes.getLength();i++){
 
            for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){ //첫번째 자식을 시작으로 마지막까지 다음 형제를 실행
 
               try {
                      
                      if(node.getNodeName().equals("SIGUNCD")){
                        //System.out.println(node.getTextContent());
                         SIGUNCD = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("SIGUNNM")){
                        //System.out.println(node.getTextContent());
                         SIGUNNM = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("PRODCD")){
                        //System.out.println(node.getTextContent());
                         prodcd = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("PRICE")){
                         //System.out.println(node.getTextContent());
                         price = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("DIFF")){
                         //System.out.println(node.getTextContent());
                         diff = node.getTextContent().toString();
                      }
                    
               } catch (Exception e) {
                      e.printStackTrace();
                      System.err.println(e.getClass().getName()+": "+e.getMessage());
                      System.exit(0);
                }
            }
            
            
            //try
            //{
               //System.out.println(tradedt);
            stmt = c.createStatement();
            sql = "UPDATE oilavgsigunprice SET "
               + "siguncd=" + "'" + SIGUNCD + "'" + ", "
               + "price=" + "'" + price + "'" + ", "
               + "diff=" + "'" + diff + "' " 
               + "WHERE " + "sigunnm=" + "'" + SIGUNNM + "' "
               + "and " + "prodcd=" + "'" + prodcd + "';";
            stmt.executeUpdate(sql);
                
                
            //} catch (Exception e) {
                
            //}
        }
        System.out.println("Records update successfully");
        stmt.close();
        c.commit();
        c.close();
        
    }
    
    private void start4() throws Exception{
       Connection c = null;
        Statement stmt = null;
        String sql = null;
       
        
        String DATE = null;
        String prodcd = null;
        String price = null;
        
        URL url = new URL("http://www.opinet.co.kr/api/avgRecentPrice.do?out=xml&code=F061160929");
        URLConnection connection = url.openConnection();

        Document doc = parseXML(connection.getInputStream());
        doc.getDocumentElement().normalize();
        NodeList descNodes = doc.getElementsByTagName("OIL");
        
        System.out.println(descNodes.getLength());
        
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection("jdbc:postgresql://202.30.30.121:5433/postgres","postgres", "postgres");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");
        
        stmt = c.createStatement();
       sql = "DELETE FROM oilavgrecentprice";
        stmt.executeUpdate(sql);
 
        for(int i=0; i<descNodes.getLength();i++){
 
            for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){ //첫번째 자식을 시작으로 마지막까지 다음 형제를 실행
 
               try {
                      
                      if(node.getNodeName().equals("DATE")){
                        //System.out.println(node.getTextContent());
                         DATE = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("PRODCD")){
                        //System.out.println(node.getTextContent());
                         prodcd = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("PRICE")){
                         //System.out.println(node.getTextContent());
                         price = node.getTextContent().toString();
                      }
                    
               } catch (Exception e) {
                      e.printStackTrace();
                      System.err.println(e.getClass().getName()+": "+e.getMessage());
                      System.exit(0);
                }
            }
            
            
            //try
            //{
               //System.out.println(tradedt);
            
                sql = "INSERT INTO oilavgrecentprice (date, prodcd, price) "
                     + "VALUES (" + "'" + DATE + "'" + ", " + "'" + prodcd + "'" + ", " + "'" + price + "'" + ");";
                stmt.executeUpdate(sql);
                
                
            //} catch (Exception e) {
                
            //}
        }
        System.out.println("Records update successfully");
        stmt.close();
        c.commit();
        c.close();
        
    }
    
    private void start5() throws Exception{
       Connection c = null;
        Statement stmt = null;
        String sql = null;
       
        
        String WEEK = null;
        String STA_DT = null;
        String END_DT = null;
        String AREA_CD = null;
        String prodcd = null;
        String price = null;
        
        URL url = new URL("http://www.opinet.co.kr/api/avgLastWeek.do?code=F061160929&out=xml");
        URLConnection connection = url.openConnection();

        Document doc = parseXML(connection.getInputStream());
        doc.getDocumentElement().normalize();
        NodeList descNodes = doc.getElementsByTagName("OIL");
        
        System.out.println(descNodes.getLength());
        
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection("jdbc:postgresql://202.30.30.121:5433/postgres","postgres", "postgres");
        c.setAutoCommit(false);
        System.out.println("Opened database successfully");
        
        stmt = c.createStatement();
       sql = "DELETE FROM oilavglastweek";
        stmt.executeUpdate(sql);
 
        for(int i=0; i<descNodes.getLength();i++){
 
            for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){ //첫번째 자식을 시작으로 마지막까지 다음 형제를 실행
 
               try {
                      
                      if(node.getNodeName().equals("WEEK")){
                        //System.out.println(node.getTextContent());
                         WEEK = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("STA_DT")){
                          //System.out.println(node.getTextContent());
                         STA_DT = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("END_DT")){
                          //System.out.println(node.getTextContent());
                         END_DT = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("AREA_CD")){
                          //System.out.println(node.getTextContent());
                         AREA_CD = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("PRODCD")){
                        //System.out.println(node.getTextContent());
                         prodcd = node.getTextContent().toString();
                      }else if(node.getNodeName().equals("PRICE")){
                         //System.out.println(node.getTextContent());
                         price = node.getTextContent().toString();
                      }
                    
               } catch (Exception e) {
                      e.printStackTrace();
                      System.err.println(e.getClass().getName()+": "+e.getMessage());
                      System.exit(0);
                }
            }
            
            
            //try
            //{
               //System.out.println(tradedt);
               
                sql = "INSERT INTO oilavglastweek (week, stadt, enddt, areacd, prodcd, price) "
                     + "VALUES (" + "'" + WEEK + "'" + ", " + "'" + STA_DT + "'" + ", " + "'" + END_DT + "'" + ", " + "'" + AREA_CD + "'" + ", " + "'" + prodcd + "'" + ", " + "'" + price + "'" + ");";
                stmt.executeUpdate(sql);
                
                
            //} catch (Exception e) {
                
            //}
        }
        System.out.println("Records update successfully");
        stmt.close();
        c.commit();
        c.close();
        
    }

    private Document parseXML(InputStream stream) throws Exception{
 
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
 
        try{
 
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
 
            doc = objDocumentBuilder.parse(stream);
 
        }catch(Exception ex){
            throw ex;
        }       
 
        return doc;
    }
      
   public void run() {
      while(true) {
         SimpleDateFormat sdf = new SimpleDateFormat("HHmmss", Locale.KOREA);
         Date dTime = new Date();
         String nowdate = sdf.format(dTime);
         //System.out.println(nowdate);
         
         if(nowdate.equals("060500"))
         {
            try {
                  //System.out.println("시작");
                  start1();
                  
                  try {
                     Thread.sleep(300);
                  } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
                  
                  start2();
                  
                  try {
                     Thread.sleep(300);
                  } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
                  
                  start3();
                  
                  try {
                     Thread.sleep(300);
                  } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
                  
                  start4();
                  
                  try {
                     Thread.sleep(300);
                  } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
                  
                  start5();
                  
                  try {
                     Thread.sleep(300);
                  } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
                  
            } catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
         
         try {
            Thread.sleep(300);
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
      }
   }
}



class Thread2 extends Thread {
   ServerSocket serverSocket = null;
    Socket socket = null;
    BufferedWriter bw = null;     //데이터 쓰기
    BufferedReader br;
    Connection c = null;
    Statement stmt = null;
    String select;
    String[] str;
    String sql;
    String email = new String();
    String password;
    
   public void run() {
       
     //소켓 준비
        try {
                serverSocket = new ServerSocket(20001);       //7777번 포트로 대기중
                System.out.println("서버가 준비되었습니다.");
                email = null;
                
			      while(true){    //무한루프로 열어놓음
			                    socket = serverSocket.accept();
			                    System.out.println("클라이언트가 연결되었습니다 - " + socket.getInetAddress());
			  
			                    try{
				                    bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				                    
				                    //bw.write("Hello World");
				                   
				                    //bw.flush();    //출력 버퍼를 비우는 메서드! (반드시 써줄것!)
				                    
				                    
				                    select = br.readLine();
				                    System.out.println(select);
				                    
				                    str = new String(select).split(" ");
				                    
				                    if (str[0].equals("user")){
					                    try {
					                         Class.forName("org.postgresql.Driver");
					                         c = DriverManager.getConnection("jdbc:postgresql://202.30.30.121:5433/postgres","postgres", "postgres");
					                         c.setAutoCommit(false);
					                       
					                         System.out.println("Opened database successfully");
					                         stmt = c.createStatement();
					                         ResultSet rs = stmt.executeQuery("SELECT * FROM userinfo WHERE email='" + str[1] + "'" + ";");
					                         
					                         while( rs.next()){
					                        	 email = rs.getString("email");
					                         }
					                         
					                         System.out.println(email);
					                         System.out.println(str[1]);
					                         
					                         
					                         if (email == null) {
					                        	 sql = "INSERT INTO userinfo (email, password) VALUES (" + "'" + str[1] + "'" + ", " + "'" + str[2] + "'"+ ");";
						                         stmt.executeUpdate(sql);
					                        	 
					                         }
					                         else if (email.equals(str[1]))
					                         {
					                        	 System.out.println("same");
					                        	 bw.write("same");
					                        	 bw.flush();
					                         }
					                         rs.close();
					                         stmt.close();
							                 c.commit();
							                 c.close();
					                         
					                     } catch (Exception e) {
					                        e.printStackTrace();
					                        System.err.println(e.getClass().getName()+": "+e.getMessage());
					                        System.exit(0);
					                     }
				                    } else if (str[0].equals("login")){
				                    	try {
					                         Class.forName("org.postgresql.Driver");
					                         c = DriverManager.getConnection("jdbc:postgresql://202.30.30.121:5433/postgres","postgres", "postgres");
					                         c.setAutoCommit(false);
					                       
					                         System.out.println("Opened database successfully");
					                         stmt = c.createStatement();
					                         ResultSet rs = stmt.executeQuery("SELECT * FROM userinfo WHERE email='" + str[1] + "'" + " and " + "password='" + str[2] + "'" + ";");
					                         
					                         while( rs.next()){
					                        	 password = rs.getString("password");
					                         }
					                         
					                         System.out.println(password);
					                         System.out.println(str[2]);
					                         
					                         if (password.equals(str[2])) {
					                        	 System.out.println("login");
					                        	 bw.write("login");
					                        	 bw.flush();
					                         }
					                         else
					                         {
					                        	 System.out.println("df");
					                        	 bw.write("df");
					                        	 bw.flush();
					                         }
					                         
					                         rs.close();
					                         stmt.close();
							                 c.commit();
							                 c.close();
					                         
					                     } catch (Exception e) {
					                        e.printStackTrace();
					                        System.err.println(e.getClass().getName()+": "+e.getMessage());
					                        System.exit(0);
					                     }
				                    } else if (str[0].equals("aroundAllsave")) {
				                    	try {
											aroundallsave(str[1], str[2], str[3], str[4], str[5]);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
				                    } else if (str[0].equals("aroundAllmark")) {
				                    	try {
											aroundallmark(str[1], str[2]);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
				                    } else if (str[0].equals("aroundAllselect")) {
				                    	try {
											aroundallselect(str[1], str[2]);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
				                    }
				                   
			                    } catch (IOException e) {
			                        e.printStackTrace();
			                    } finally {
			                            try{bw.close();} catch(IOException e){}
			                            try{socket.close();} catch(IOException e){}
			                    }
			      }
        }catch (IOException e) {
        	e.printStackTrace();
        }
  	}
   
   private void aroundallsave(String a, String b, String c1, String d, String e) throws Exception {
	   Connection c = null;
       Statement stmt = null;
       String sql = null;
	   String UNI_ID = null;
       String POLL_DIV_CO = null;
       String OS_NM = null;
       String PRICE = null;
       String DISTANCE = null;
       String GIS_X_COOR = null;
       String GIS_Y_COOR = null;
       String GooGle_X = null;
       String GooGle_Y = null;
       
       String Servicekey="apikey=85a321c3cd39c2f2651dff630c960c68";
       String getInfo="https://apis.daum.net/local/geo/transcoord?";
       String fromCoord="fromCoord=";
       String toCoord="toCoord=";
       String x="x=";
       String y="y=";
       String format="output=xml";
       

       try {
    	   
    	   
       	   URL url = new URL("http://www.opinet.co.kr/api/aroundAll.do?code=F061160929&x=" + a + "&y=" + b + "&radius=" + c1 + "&sort=" + d + "&prodcd=" + e + "&out=xml");
           URLConnection connection = url.openConnection();

           Document doc = parseXML(connection.getInputStream());
           doc.getDocumentElement().normalize();
         
           NodeList descNodes = doc.getElementsByTagName("OIL");
           
           System.out.println(descNodes.getLength());
       	
       	
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://202.30.30.121:5433/postgres","postgres", "postgres");
           c.setAutoCommit(false);
           System.out.println("Opened database successfully");
           
           stmt = c.createStatement();
           sql = "DELETE FROM aroundall";
           stmt.executeUpdate(sql);
           
           for(int i=0; i<descNodes.getLength();i++){
                
               for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){ //첫번째 자식을 시작으로 마지막까지 다음 형제를 실행
    
                  try {
                         
                         if(node.getNodeName().equals("UNI_ID")){
                           //System.out.println(node.getTextContent());
                       	  UNI_ID = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("POLL_DIV_CO")){
                             //System.out.println(node.getTextContent());
                       	  POLL_DIV_CO = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("OS_NM")){
                             //System.out.println(node.getTextContent());
                       	  OS_NM = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("PRICE")){
                             //System.out.println(node.getTextContent());
                       	  PRICE = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("DISTANCE")){
                           //System.out.println(node.getTextContent());
                       	  DISTANCE = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("GIS_X_COOR")){
                            //System.out.println(node.getTextContent());
                       	  GIS_X_COOR = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("GIS_Y_COOR")){
                                //System.out.println(node.getTextContent());
                       	  GIS_Y_COOR = node.getTextContent().toString();
                         }
                       
                  } catch (Exception e1) {
                         e1.printStackTrace();
                         System.err.println(e1.getClass().getName()+": "+e1.getMessage());
                         System.exit(0);
                  }
              }
               URL url1 = new URL(getInfo + Servicekey + "&" + fromCoord + "KTM" + "&" + toCoord + "WGS84" + "&" + x + GIS_X_COOR + "&" + y + GIS_Y_COOR + "&" + format);
               URLConnection connection1 = url1.openConnection();

               System.out.println(url1);
               
               Document doc1 = parseXML(connection1.getInputStream());
               doc1.getDocumentElement().normalize();
               
               NodeList descNodes1 = doc1.getElementsByTagName("result");
   
               Node node1 = descNodes1.item(0);
               
               NamedNodeMap Attrs = node1.getAttributes();
               
               GooGle_Y = Attrs.getNamedItem("x").getNodeValue().toString();
               GooGle_X = Attrs.getNamedItem("y").getNodeValue().toString();
               
               		sql = "INSERT INTO aroundall (uni_id, poll_div_co, os_nm, price, distance, gis_x_coor, gis_y_coor, prodcd, google_x, google_y) "
               				+ "VALUES (" + "'" + UNI_ID + "'" + ", " + "'" + POLL_DIV_CO + "'" + ", " + "'" + OS_NM + "'"
               				+ ", " + "'" + PRICE + "'" + ", " + "'" + DISTANCE + "'"
               				+ ", " + "'" + GIS_X_COOR + "'" + ", " + "'" + GIS_Y_COOR + "'" + ", " + "'" + e + "'"
               				+ ", " + "'" + GooGle_X + "'" + ", " + "'" + GooGle_Y + "'" + ");";
                   
						stmt.executeUpdate(sql);
                     
           }
           System.out.println("Records insert aroundall successfully");
           stmt.close();
           c.commit();
           c.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	   
   	}
   
   private void aroundallselect(String a, String b) throws Exception {
	   Connection c = null;
       Statement stmt = null;
       
       String OS_NM = null;
       String PRICE = null;
       String PRODCD = null;
       String GIS_X_COOR = null;
       String GIS_Y_COOR = null;

       try {
    	   
    	   Class.forName("org.postgresql.Driver");
    	   c = DriverManager.getConnection("jdbc:postgresql://202.30.30.121:5433/postgres","postgres", "postgres");
    	   c.setAutoCommit(false);
           System.out.println("Opened database successfully");
          
           stmt = c.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT * FROM aroundall WHERE google_x='" + str[1] + "'" + " and " + "google_y='" + str[2] + "'" + ";");
           
           while (rs.next()){
        	   OS_NM = rs.getString("os_nm");
        	   PRICE = rs.getString("price");
        	   PRODCD = rs.getString("prodcd");
        	   GIS_X_COOR = rs.getString("gis_x_coor");
        	   GIS_Y_COOR = rs.getString("gis_y_coor");
        	   
           }    	   
		           System.out.println("Records select aroundall successfully");
		           
		           bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		           bw.write(OS_NM + "!" + PRODCD + "!" + PRICE + "!" + GIS_X_COOR + "!" + GIS_Y_COOR);
		           bw.flush();
		           
		           stmt.close();
		           c.commit();
		           c.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	   
   	}
   
   private void aroundallmark(String a, String b) throws Exception {
	   Connection c = null;
       Statement stmt = null;
       String sql = null;
	   String UNI_ID = null;
       String VAN_ADR = null;
       String NEW_ADR = null;
       String TEL = null;
       String LPG_YN = null;
       String MAINT_YN = null;
       String CAR_WASH_YN = null;
       String CVS_YN = null;
       String OS_NM = null;
       String GIS_X_COOR = null;
       String GIS_Y_COOR = null;
       String UNI_ID_1 = null;
       String PRICE = null;
       String PRODCD = null;
       String GooGle_X = null;
       String GooGle_Y = null;
       

       try {
    	   
    	   Class.forName("org.postgresql.Driver");
    	   c = DriverManager.getConnection("jdbc:postgresql://202.30.30.121:5433/postgres","postgres", "postgres");
    	   c.setAutoCommit(false);
           System.out.println("Opened database successfully");
          
           stmt = c.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT * FROM aroundall WHERE gis_x_coor='" + str[1] + "'" + " and " + "gis_y_coor='" + str[2] + "'" + ";");
           
           while (rs.next()){
        	   UNI_ID_1 = rs.getString("uni_id");
        	   GIS_X_COOR = rs.getString("gis_x_coor");
        	   GIS_Y_COOR = rs.getString("gis_y_coor");
        	   PRICE = rs.getString("price");
        	   PRODCD = rs.getString("prodcd");
        	   GooGle_X = rs.getString("google_x");
        	   GooGle_Y = rs.getString("google_y");
        	   
           }
           System.out.println(UNI_ID_1);
           System.out.println(PRICE);
           System.out.println(PRODCD);
           System.out.println(GIS_X_COOR);
           System.out.println(GIS_Y_COOR);
           System.out.println(GooGle_X);
           System.out.println(GooGle_Y);
    	   
    	   
    	   URL url = new URL("http://www.opinet.co.kr/api/detailById.do?code=F061160929&id=" + UNI_ID_1 + "&out=xml");
           URLConnection connection = url.openConnection();

           Document doc = parseXML(connection.getInputStream());
           doc.getDocumentElement().normalize();
         
           NodeList descNodes = doc.getElementsByTagName("OIL");
           
               for(Node node = descNodes.item(0).getFirstChild(); node!=null; node=node.getNextSibling()){ //첫번째 자식을 시작으로 마지막까지 다음 형제를 실행
    
                  try {
                         
                         if(node.getNodeName().equals("UNI_ID")){
                           //System.out.println(node.getTextContent());
                        	 UNI_ID = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("OS_NM")){
                             //System.out.println(node.getTextContent());
                        	 OS_NM = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("VAN_ADR")){
                             //System.out.println(node.getTextContent());
                        	 VAN_ADR = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("NEW_ADR")){
                             //System.out.println(node.getTextContent());
                        	 NEW_ADR = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("TEL")){
                             //System.out.println(node.getTextContent());
                        	 TEL = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("LPG_YN")){
                           //System.out.println(node.getTextContent());
                        	 LPG_YN = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("MAINT_YN")){
                            //System.out.println(node.getTextContent());
                        	 MAINT_YN = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("CAR_WASH_YN")){
                                //System.out.println(node.getTextContent());
                        	 CAR_WASH_YN = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("CVS_YN")){
                             //System.out.println(node.getTextContent());
                        	 CVS_YN = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("GIS_X_COOR")){
                             //System.out.println(node.getTextContent());
                        	 GIS_X_COOR = node.getTextContent().toString();
                         }else if(node.getNodeName().equals("GIS_Y_COOR")){
                             //System.out.println(node.getTextContent());
                        	 GIS_Y_COOR = node.getTextContent().toString();
                         }
                       
                  } catch (Exception e1) {
                         e1.printStackTrace();
                         System.err.println(e1.getClass().getName()+": "+e1.getMessage());
                         System.exit(0);
                  }
              }
               	
               		sql = "INSERT INTO aroundallmark (uni_id, os_nm, van_adr, new_adr, tel, lpg_yn, maint_yn, car_wash_yn, cvs_yn, gis_x_coor, gis_y_coor, prodcd, price, google_x, google_y) "
               				+ "VALUES (" + "'" + UNI_ID + "'" + ", " + "'" + OS_NM + "'" + ", " + "'" + VAN_ADR
               				+ "'" + ", " + "'" + NEW_ADR + "'" + ", " + "'" + TEL + "'" + ", " + "'" + LPG_YN
               				+ "'" + ", " + "'" + MAINT_YN + "'" + ", " + "'" + CAR_WASH_YN + "'" + ", " + "'" + CVS_YN + "'"
               				+ ", " + "'" + GIS_X_COOR + "'" + ", " + "'" + GIS_Y_COOR + "'" + ", " + "'" + PRODCD + "'" + ", " + "'" + PRICE + "'" + ", " + "'" + GooGle_X + "'" + ", " + "'" + GooGle_Y + "'" +");";
                   
						stmt.executeUpdate(sql);
		           System.out.println("Records insert aroundall successfully");
		           stmt.close();
		           c.commit();
		           c.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	   
   	}
 
   
   private Document parseXML(InputStream stream) throws Exception{
	   
       DocumentBuilderFactory objDocumentBuilderFactory = null;
       DocumentBuilder objDocumentBuilder = null;
       Document doc = null;

       try{

           objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
           objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

           doc = objDocumentBuilder.parse(stream);

       }catch(Exception ex){
           throw ex;
       }       

       return doc;
   }
}

public class OilServer {

        public static void main(String[] args) {           
           
           Thread1 t1 = new Thread1();
           Thread2 t2 = new Thread2();
           
           t1.start();
           t2.start();
                 
                 
        }
}