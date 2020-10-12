import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogger {

    private static Logger log,log1;
    static FileHandler fileHandler,fileHandler2;

    public TestLogger() throws IOException {
        log = Logger.getLogger(this.getClass().getName());

        log.setLevel(Level.INFO);
        log1 = Logger.getLogger("com.xiya");
        ConsoleHandler consoleHandler =new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        log.addHandler(consoleHandler);
         fileHandler = new FileHandler("testlog.log");
         fileHandler2 = new FileHandler("templog.log");
        fileHandler.setLevel(Level.INFO);
        fileHandler.setFormatter(new LoggerFormatter());
        fileHandler2.setLevel(Level.INFO);
        fileHandler2.setFormatter(new LoggerFormatter());
        log.addHandler(fileHandler);
        log1.addHandler(fileHandler2);
        log.info("start");
        log1.info("start");
//        log1.fine("333");



    }
        public static void main(String[] args) throws IOException {
        TestLogger instance=new TestLogger();

            log.info("hi");
            log1.warning("warning");
            boolean run=true;
            while(run){
                Scanner sc=new Scanner(System.in);
                String s=sc.next();
                if(s.equals("close")){
                    run=false;
                    File temp=new File("templog.log") ;
                    if(temp.exists()){
                        System.out.println(temp.getAbsolutePath());
                        System.out.println("delete");

                        fileHandler2.close();//必須先關閉IO才能刪除檔案
                        boolean delete=temp.delete();
                        if (delete==false){
                            System.out.println("未刪除");
                        }
                    }





                }else{
                    log.info(s);
                    System.out.println(s);




                }


            }



        }

    }
