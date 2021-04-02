/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpedidos;

import enums.Role;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.crypto.SecretKey;
import permissions.ControllerFunctionalities;
import permissions.PermissionsByRole;
import utils.AESKey;
import utils.JPedidosException;

/**
 *
 * @author Paulo
 */
public class Settings {
    public static SecretKey SecretKey = AESKey.generateAESKey(128);
    public static String DbHost;
    public static String DbUser;
    public static String DbPass;
    public static String DbDriver;
    public static Map<Role, List<ControllerFunctionalities>> Permissions = PermissionsByRole.permissions();
    
    public static void Load(String path) {
        File fileSettings = new File(path);
        if (!fileSettings.exists() || !fileSettings.isFile())
            throw new JPedidosException("Arquivo de configuração inválido");
        Properties settings = new Properties();
        try {
            settings.load(new FileReader(fileSettings));
        } catch(IOException ex) {
            throw new JPedidosException("Falha ao carregar configurações", ex);
        }
        
        DbHost = settings.getProperty("HOST");
        DbUser = settings.getProperty("USER");
        DbPass = settings.getProperty("PASS");
        DbDriver = settings.getProperty("DRIVER");
        
        try {
            String base64Key = settings.getProperty("SECRET");
            SecretKey = AESKey.decodeBase64ToAESKey(base64Key);   
        } catch (Exception ex) {
            throw new JPedidosException("Falha ao decodificar chave privada", ex);
        }
    }
}
