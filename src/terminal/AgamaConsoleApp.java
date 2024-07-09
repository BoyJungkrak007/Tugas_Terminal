package terminal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AgamaConsoleApp {

    private Connection conn;

    public AgamaConsoleApp() {
        buatKoneksi();
    }

    private void buatKoneksi() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/tes", "root", "");
            System.out.println("Koneksi ke database berhasil.");
        } catch (SQLException e) {
            System.out.println("Koneksi ke database gagal: " + e.getMessage());
        }
    }

    private void simpanAgama(String kodeAgama, String namaAgama) {
        try {
            String sql = "INSERT INTO agama (kode_agama, nama_agama) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kodeAgama);
            stmt.setString(2, namaAgama);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data agama berhasil disimpan.");
            }
        } catch (SQLException e) {
            System.out.println("Error saat menyimpan data: " + e.getMessage());
        }
    }

    private void perbaruiAgama(String kodeAgama, String namaAgama) {
        try {
            String sql = "UPDATE agama SET nama_agama = ? WHERE kode_agama = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, namaAgama);
            stmt.setString(2, kodeAgama);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Data agama berhasil diperbarui.");
            }
        } catch (SQLException e) {
            System.out.println("Error saat memperbarui data: " + e.getMessage());
        }
    }

    private void hapusAgama(String kodeAgama) {
        try {
            String sql = "DELETE FROM agama WHERE kode_agama = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kodeAgama);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Data agama berhasil dihapus.");
            }
        } catch (SQLException e) {
            System.out.println("Error saat menghapus data: " + e.getMessage());
        }
    }

    private void tampilkanDataAgama() {
        try {
            String sql = "SELECT kode_agama, nama_agama FROM agama";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("Kode Agama | Nama Agama");
            System.out.println("-----------------------");
            while (rs.next()) {
                String kodeAgama = rs.getString("kode_agama");
                String namaAgama = rs.getString("nama_agama");
                System.out.println(kodeAgama + " | " + namaAgama);
            }
        } catch (SQLException e) {
            System.out.println("Error saat memuat data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        AgamaConsoleApp app = new AgamaConsoleApp();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Pilih operasi: ");
            System.out.println("1. Tambah Agama");
            System.out.println("2. Perbarui Agama");
            System.out.println("3. Hapus Agama");
            System.out.println("4. Tampilkan Data Agama");
            System.out.println("5. Keluar");

            int pilihan = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan kode agama: ");
                    String kodeAgamaTambah = scanner.nextLine();
                    System.out.print("Masukkan nama agama: ");
                    String namaAgamaTambah = scanner.nextLine();
                    app.simpanAgama(kodeAgamaTambah, namaAgamaTambah);
                    break;
                case 2:
                    System.out.print("Masukkan kode agama: ");
                    String kodeAgamaPerbarui = scanner.nextLine();
                    System.out.print("Masukkan nama agama baru: ");
                    String namaAgamaPerbarui = scanner.nextLine();
                    app.perbaruiAgama(kodeAgamaPerbarui, namaAgamaPerbarui);
                    break;
                case 3:
                    System.out.print("Masukkan kode agama: ");
                    String kodeAgamaHapus = scanner.nextLine();
                    app.hapusAgama(kodeAgamaHapus);
                    break;
                case 4:
                    app.tampilkanDataAgama();
                    break;
                case 5:
                    System.out.println("Keluar dari aplikasi.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
