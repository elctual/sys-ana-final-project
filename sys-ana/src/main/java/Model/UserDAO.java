/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author elifa
 */
import Model.User;
import java.util.List;
import java.util.ArrayList;

public class UserDAO {

    // Kullanıcı adı (u) ve şifreye (p) göre kullanıcıyı getirir (Giriş/Login işlemi için kullanılır)
    public User getUserByCredentials(String u, String p) {
        // TODO: Veritabanından kullanıcı adı ve şifre eşleşmesini kontrol edecek kod buraya yazılacak
        return null; 
    }

    // Tüm kullanıcıları liste halinde getirir
    public List<User> getAllUsers() {
        // TODO: Veritabanından tüm kullanıcıları çekecek kod
        return new ArrayList<>(); 
    }

    // Yeni bir kullanıcı oluşturur/ekler (Başarılı olursa true döner)
    public boolean createUser(User u) {
        // TODO: Gelen 'u' nesnesini veritabanına kaydedecek kod (Örn: INSERT INTO...)
        return false; 
    }

    // Var olan bir kullanıcının bilgilerini günceller (Başarılı olursa true döner)
    public boolean updateUser(User u) {
        // TODO: Gelen 'u' nesnesindeki verilerle veritabanını güncelleyecek kod (Örn: UPDATE...)
        return false; 
    }

    // ID'si verilen kullanıcıyı siler (Başarılı olursa true döner)
    public boolean deleteUser(int id) {
        // TODO: İlgili kullanıcıyı veritabanından silecek kod (Örn: DELETE FROM...)
        return false; 
    }

    // Sadece ID'si verilen kullanıcının şifresini günceller (Başarılı olursa true döner)
    public boolean updatePassword(int id, String pwd) {
        // TODO: Veritabanında sadece ilgili kullanıcının şifre alanını güncelleyecek kod
        return false; 
    }
}
