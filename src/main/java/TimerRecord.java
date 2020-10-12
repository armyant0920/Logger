import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerRecord implements ActionListener {

    private static Timer timer;
    private int delay = 1000;
    private String dirPath;
    private final String historyPath = "History.txt";
    private final String tempFilename = "temp";
    private final String SYS_MESSAGE = "Logger";
    private final String SYS_LAST_CLOSE = "上次正常關閉時間:";
    private final String SYS_CREATE_NEW = "歷史檔案不存在,重新建立";

    private final String SYS_ABNORMAL = "上次程式異常關閉";
    private static final String SYS_OPEN = "Open_Time ";
    private static final String SYS_CLOSE = "Close_Time ";
    private static final String SYS_ABNORMAL_CLOSE="Abnormal_Time ";
    private static TimerRecord instance;
    private static File tempFile;
    private static File historyFile;
    private static boolean abnormal_shutdown=false;

    public TimerRecord() throws IOException {
        java.net.URL url = this.getClass().getResource("/");

        dirPath = this.getClass().getResource("/").getPath();
        System.out.println(dirPath);
        System.out.println(url);
        tempFile = new File(dirPath + tempFilename);//臨時記錄檔
        historyFile = new File(dirPath + historyPath);

        if (historyFile.exists()) {
            //如果history存在
            if (!tempFile.exists()) {
                //且tempFile不存在,表示正常關閉,顯示正常關閉時間

                JOptionPane.showMessageDialog(null, readLastTime(historyFile), SYS_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
                System.out.println(readLastTime(historyFile));
            } else {
                //如果tempFile存在,沒有把暫存檔刪掉表示異常關閉,顯示異常關閉時間
                abnormal_shutdown=true;
                //將此次執行時間加入歷史文件
                writeData(historyFile,  SYS_ABNORMAL_CLOSE+ readRecord(tempFile));
                deleteFile(tempFile);
                JOptionPane.showMessageDialog(null, readLastTime(historyFile), SYS_ABNORMAL, JOptionPane.WARNING_MESSAGE);
                System.out.println(readLastTime(historyFile));

            }

        } else {
            //如果檔案不存在,建立新檔案
            JOptionPane.showMessageDialog(null, SYS_CREATE_NEW, SYS_MESSAGE, JOptionPane.INFORMATION_MESSAGE);

            createFile(historyFile);

            //預設如果歷史文件不存在,就代表重新開始,檢查是否有過去留下來的temp,有的話把它也刪掉
            deleteFile(tempFile);
        }

        System.out.println("創建臨時檔案");
        createFile(tempFile);
        writeData(tempFile, getTimeText());
        writeData(historyFile, SYS_OPEN + getTimeText());

        timer = new Timer(delay, this);
        timer.start();

    }

    public static TimerRecord getInstance() {
        if (instance == null) {
            synchronized (TimerRecord.class) {
                try {
                    instance = new TimerRecord();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

    public void actionPerformed(ActionEvent e) {

        try {
            writeData(tempFile, getTimeText());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static void exit() {
        timer.stop();
        try {
            writeData(historyFile, SYS_CLOSE + getTimeText());

        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteFile(tempFile);

    }

    private static void writeData(File file, String s) throws IOException {
        if (file.exists()) {
            BufferedWriter bufw = new BufferedWriter(new FileWriter(file, true));//由於希望記錄是往下新增,使用append
            bufw.write(s + "\n");//將獲取文字內容寫入到字元輸出流
            bufw.close();//關閉檔案
        } else {
            throw new FileNotFoundException("檔案不存在");
        }

    }

    private static String getTimeText() {

        //取得目前時間,並將格式化後的資料存成字串
        Date date = new Date();
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        return sdFormat.format(date);//獲取文字內容

    }

    private static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {//如果是一個標轉檔案
                file.delete();

            } else {
                //如果是一整個資料夾,把資料夾下所有的文件刪除,此案例雖然用不到,但之後可以做為其他專案參考
                File fileLists[] = file.listFiles();
                for (File f : fileLists) {

                    deleteFile(f);
                }
            }
        }
    }

    private static void createFile(File file) {
        if (!file.exists()) {//傳入的檔案路徑不存在檔案
            file.getParentFile().mkdirs();//避免連parent資料夾也不存在,幫它直接建立一個
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readLastTime(File file) throws IOException {



        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return null;
        }


        BufferedReader br = new BufferedReader(new FileReader(file));
        String open = "";
        String close = "";
        String current="";
        while (br.ready()) {
            current=br.readLine();
            if (current.startsWith(SYS_OPEN)) {

                open = current;
               // System.out.println("open"+open);
            } else if(abnormal_shutdown==true){
                if(current.startsWith(SYS_ABNORMAL_CLOSE)){
                    close=current;
                }


            }else{
                if(current.startsWith(SYS_CLOSE)){
                    close=current;
                }

            }

        }
        br.close();

        return open+"\n"+close;//這邊目前只打算抓最後一筆

    }

    private static String readRecord(File file) throws IOException {
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return null;


        }
        String currentLine = "";

        BufferedReader br = new BufferedReader(new FileReader(file));
        while (br.ready()) {
            currentLine = br.readLine();
        }
        br.close();
        return currentLine;//這邊目前只打算抓最後一筆
    }

}






