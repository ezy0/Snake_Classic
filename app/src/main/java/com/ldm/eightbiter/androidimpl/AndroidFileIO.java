package com.ldm.eightbiter.androidimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;

import com.ldm.eightbiter.FileIO;

public class AndroidFileIO implements FileIO {
    AssetManager assets;
    String rutaAlmacenamientoInterno;

    public AndroidFileIO(Context context, AssetManager assets) {
        this.assets = assets;
        //this.rutaAlmacenamientoExterno = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator;
        this.rutaAlmacenamientoInterno = context.getFilesDir().getAbsolutePath() + File.separator;
    }

    @Override
    public InputStream leerAsset(String nombreArchivo) throws IOException {
        return assets.open(nombreArchivo);
    }

    @Override
    public InputStream leerArchivo(String nombreArchivo) throws IOException {
        return new FileInputStream(rutaAlmacenamientoInterno + nombreArchivo);
    }

    @Override
    public OutputStream escribirArchivo(String nombreArchivo) throws IOException {
        return new FileOutputStream(rutaAlmacenamientoInterno + nombreArchivo);
    }

}

