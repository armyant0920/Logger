import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public  class Logger implements ActionListener {

    private Timer timer;
    private int delay=1;
    private String dirPath;
    private String filePath;
    private static Logger instance;
    private File tempFile;
    private File history;
    public Logger(){
        java.net.URL url=this.getClass().getResource("/");

        System.out.println(url);
        timer=new Timer(delay,this);
//        timer.start();
        File save=new File((String)url,"save.txt");
        String dirpath = saveDia.getDirectory();//獲取儲存檔案路徑並儲存到字串中。
        String fileName = saveDia.getFile() + ".txt";////獲取打儲存檔名稱並儲存到字串中

        if (dirpath == null || fileName == null)//判斷路徑和檔案是否為空
            return;//空操作
        else
            file = new File(dirpath, fileName);//檔案不為空，新建一個路徑和名稱

        try {
            BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
            String text = textOutput.getText();//獲取文字內容
            bufw.write(text);//將獲取文字內容寫入到字元輸出流
            bufw.close();//關閉檔案
        } catch (IOException e1) {
            e1.printStackTrace();//丟擲IO異常
        }




    }
    public Logger getInstance(){
        if(instance==null){
            synchronized (Logger.class){
            instance=new Logger();
            }
        }
        return instance;



    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }

/*    public static void record(){
        String dirpath = saveDia.getDirectory();//獲取儲存檔案路徑並儲存到字串中。
        String fileName = saveDia.getFile() + ".txt";////獲取打儲存檔名稱並儲存到字串中

        if (dirpath == null || fileName == null)//判斷路徑和檔案是否為空
            return;//空操作
        else
            file = new File(dirpath, fileName);//檔案不為空，新建一個路徑和名稱

        try {
            BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
            String text = textOutput.getText();//獲取文字內容
            bufw.write(text);//將獲取文字內容寫入到字元輸出流
            bufw.close();//關閉檔案
        } catch (IOException e1) {
            e1.printStackTrace();//丟擲IO異常
        }
    }
});*/

    }






