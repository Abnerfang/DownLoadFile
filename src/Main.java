import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
  private static final String SAVEPATH = "C:\\Users\\cao.zm\\Desktop\\URL.txt";
  private static final String DOWNLOADFILE = "D:\\FontTTF";

  private static byte[] readInputStream(InputStream inputStream) throws IOException {
    byte[] buffer = new byte[1024];
    int len;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    while ((len = inputStream.read(buffer)) != -1) {
      bos.write(buffer, 0, len);
    }
    bos.close();
    return bos.toByteArray();
  }

  private static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
    URL url = new URL(urlStr);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    //设置超时间为3秒
    conn.setConnectTimeout(3 * 1000);
    //防止屏蔽程序抓取而返回403错误
    conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

    //得到输入流
    InputStream inputStream = conn.getInputStream();
    //获取自己数组
    byte[] getData = readInputStream(inputStream);

    //文件保存位置
    File saveDir = new File(savePath);
    if (!saveDir.exists()) {
      saveDir.mkdir();
    }
    File file = new File(saveDir + File.separator + fileName);
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(getData);
    if (fos != null) {
      fos.close();
    }
    if (inputStream != null) {
      inputStream.close();
    }


    System.out.println("info:" + url + " download success");

  }

  public static void main(String[] args) {
    FileInputStream fis = null;
    InputStreamReader isr = null;
    BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
    try {
      String str;
      String url[] = new String[201];
      fis = new FileInputStream(SAVEPATH);// FileInputStream
      // 从文件系统中的某个文件中获取字节
      isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
      br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
      int i = 0;
      while ((str = br.readLine()) != null) {
        url[i] = str;
        i++;
      }
      for (String a : url) {
        String[] name = a.split("/");
        downLoadFromUrl(a, name[name.length - 1], DOWNLOADFILE);
      }

    } catch (FileNotFoundException e) {
      System.out.println("找不到指定文件");
    } catch (IOException e) {
      System.out.println("读取文件失败");
    } finally {
      try {
        br.close();
        isr.close();
        fis.close();
        // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
