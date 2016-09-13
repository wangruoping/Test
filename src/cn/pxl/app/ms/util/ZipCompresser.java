package cn.pxl.app.ms.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Zip 形式でアーカイブするサンプルです。
 * ※日本語ファイル名、ディレクトリ名に未対応
 */
public class ZipCompresser {

    /**
     * 基準になるディレクトリかファイル
     */
    private File baseFile;

    /**
     * 起点になるディレクトリ（ファイル）の絶対パス
     */
    private String baseFilePath;

    /**
     * コンストラクタ
     * @param base 起点になるディレクトリ、又はファイル
     */
    public ZipCompresser(File base) {
        super();
        this.baseFile = base;
        this.baseFilePath = base.getAbsolutePath();
    }

    /**
     * baseFile を圧縮します。
     */
    public void archive() {
        // 出力先ファイル
        File zipfile =
                new File(this.baseFile.getParent(), this.baseFile.getName()
                        + ".zip");
        ZipOutputStream zos = null;
        try {
            // 出力先 OutputStream を生成
            zos = new ZipOutputStream(new FileOutputStream(zipfile));
            archive(zos, this.baseFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * file を zos に出力します。
     * @param zos zipファイル出力ストリーム
     * @param file 入力元ファイル
     */
    private void archive(ZipOutputStream zos, File file) {

        if (file.isDirectory()) {
            // ディレクトリは含まれるファイルを再起呼び出し。
            File[] files = file.listFiles();
            for (File f : files) {
                archive(zos, f);
            }
        } else {
            BufferedInputStream fis = null;
            try {
                // 入力ストリーム生成
                fis = new BufferedInputStream(new FileInputStream(file));

                // Entry 名称を取得する。
                String entryName =
                        file.getAbsolutePath().replace(this.baseFilePath, "")
                                .substring(1);

                // 出力先 Entry を設定する。
                zos.putNextEntry(new ZipEntry(entryName));

                // 入力ファイルを読み込み出力ストリームに書き込んでいく
                int ava = 0;
                while ((ava = fis.available()) > 0) {
                    byte[] bs = new byte[ava];
                    fis.read(bs);
                    zos.write(bs);
                }

                // 書き込んだら Entry を close する。
                zos.closeEntry();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}