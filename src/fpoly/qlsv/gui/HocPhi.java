/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fpoly.qlsv.gui;

import fpoly.qlsv.entity.HocPhi1;
import fpoly.qlsv.entity.SinhVien;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author hoang
 */
public class HocPhi extends javax.swing.JFrame {

    DefaultTableModel tblModel;
    private ArrayList<HocPhi1> list = new ArrayList<>();
    private List<SinhVien> lisst = new ArrayList<>();
    private int index = -1;
    String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QLGD;user=sa;password=My27012003@";

    public HocPhi() {
        initComponents();
        setLocationRelativeTo(null);
        iniTable();
        loat_Data();
        loadDataToArray1();
        filltotable();

    }

    public void iniTable() {
        tblModel = (DefaultTableModel) tblDanhSach.getModel();
        String[] nav = new String[]{"Mã sinh viên", "Tên sinh viên", "Mã khoá học", "Mã hoá đơn", "Số tiền cần đóng", "Thời hạn"};
        tblModel.setColumnIdentifiers(nav);
    }

    public void filltotable() {
        tblModel = (DefaultTableModel) tblDanhSach.getModel();
        tblModel.setRowCount(0);
        for (HocPhi1 p : list) {
            Object[] row = new Object[]{p.getMaSV(), p.getHoTen(), p.getMaKH(), p.getMaHD(), p.getTien(), p.getThoiGian()};
            tblModel.addRow(row);
        }
    }

    public void loat_Data() {
        try (Connection con = DriverManager.getConnection(connectionUrl);) {
            String sql = "select TenSV,HocPhi.* from HocPhi join SinhVien on HocPhi.MaSV = SinhVien.MaSV";
            Statement stm = con.createStatement(); //doi tuong de thuc thi cau lenh sql
            ResultSet rs = stm.executeQuery(sql);
            list.clear();
            while (rs.next()) {
                String maSV = rs.getString(1);
                String hoten = rs.getString(2);
                String maKH = rs.getString(3);
                String maHD = rs.getString(4);
                double Tien = rs.getDouble(5);
                Date ThoiHan = rs.getDate(6);

                HocPhi1 prod = new HocPhi1(maSV, hoten, maKH, maHD, Tien, ThoiHan);
                list.add(prod);
            }

            filltotable();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showTable(int index) {
        HocPhi1 hp = list.get(index);
        txtMasv.setText(hp.getMaSV());
        txtTensv.setText(hp.getHoTen());
        txtMaKH.setText(hp.getMaKH());
        txtMaHD.setText(hp.getMaHD());
        txtSoTienCanDong.setText(String.valueOf(hp.getTien()));
        txtThoiGian.setText(String.valueOf(hp.getThoiGian()));
    }

    public void clearForm() {
        txtMasv.setText("");
        txtMasv.setBackground(Color.WHITE);

        txtMaHD.setText("");
        txtMaHD.setBackground(Color.WHITE);

        txtMaKH.setText("");
        txtMaKH.setBackground(Color.WHITE);

        txtTensv.setText("");
        txtTensv.setBackground(Color.WHITE);

        txtSoTienCanDong.setText("");
        txtSoTienCanDong.setBackground(Color.WHITE);

        txtThoiGian.setText("");
        txtThoiGian.setBackground(Color.WHITE);

    }

    public boolean checkform() {
        boolean check = true;
        //check Masv
        if (txtMasv.getText().length() == 0) {
            txtMasv.setBackground(Color.YELLOW);
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sinh viên!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            check = false;
        } else {
            txtMasv.setBackground(Color.WHITE);
        }
        //check Tensv
        if (txtTensv.getText().length() == 0) {
            txtTensv.setBackground(Color.YELLOW);
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sinh viên!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            check = false;
        } else {
            txtTensv.setBackground(Color.WHITE);
        }

        //check MaKH
        if (txtMaKH.getText().length() == 0) {
            txtMaKH.setBackground(Color.YELLOW);
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khoá học!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            check = false;
        } else {
            txtMaKH.setBackground(Color.WHITE);
        }
        //check MaHD
        if (txtMaHD.getText().length() == 0) {
            txtMaHD.setBackground(Color.YELLOW);
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hoá đơn!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            check = false;
        } else {
            txtMaHD.setBackground(Color.WHITE);
        }

        //check SoTienCanDong
        if (txtSoTienCanDong.getText().length() == 0) {
            txtSoTienCanDong.setBackground(Color.YELLOW);
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền cần đóng!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            check = false;
        } else {
            try {
                int age = Integer.parseInt(txtSoTienCanDong.getText());
                if (age < 0 || age > 20000000) {
                    txtSoTienCanDong.setBackground(Color.YELLOW);
                    JOptionPane.showMessageDialog(this, "Số tiền bạn đóng đã quá 2000000đ!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    check = false;
                } else {
                    txtSoTienCanDong.setBackground(Color.WHITE);
                }
            } catch (NumberFormatException e) {
                txtSoTienCanDong.setBackground(Color.YELLOW);
                JOptionPane.showMessageDialog(this, "Không đúng định dạng!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                check = false;
            }
        }
        return check;
    }

    public void save() {
        if (checkform()) {
            try (java.sql.Connection con = DriverManager.getConnection(connectionUrl);) {
                String sql = "insert into HocPhi values (?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, txtMasv.getText());
                ps.setString(2, txtMaKH.getText());
                ps.setString(3, txtMaHD.getText());
                ps.setString(4, txtSoTienCanDong.getText());
                ps.setString(5, txtThoiGian.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Save thành công");
                ps.close();
                con.close();
                loat_Data();
                filltotable();

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Error");
            }
        }
    }

    public void updata() {
        if (checkform()) {
            try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
                String SQL = "update HocPhi set HocKy = ?, MaHoaDon = ?, SoTien = ? , ThoiHan = ? where MaSV = ?";
                PreparedStatement st = con.prepareStatement(SQL);
                st.setString(1, txtMaKH.getText());
                st.setString(2, txtMaHD.getText());
                st.setString(3, txtSoTienCanDong.getText());
                st.setString(4, txtThoiGian.getText());
                st.setString(5, txtMasv.getText());
                st.executeUpdate();
                JOptionPane.showMessageDialog(this, "Update thành công");
                con.close();
                loat_Data();
                filltotable();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Error");
            }
        }

    }

    public void delete() {
        if (txtMasv.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã sv");
            txtMasv.requestFocus();
            return;
        }
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "delete from HocPhi where MaSV = ?";
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, txtMasv.getText());
            st.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            con.close();
            loat_Data();
            filltotable();
            clearForm();
        } // Handle any errors that may have occurred.
        catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Error");
        }
    }

    public void First() {
        index = 0;
        tblDanhSach.setRowSelectionInterval(index, index);
        showTable(index);
    }

    public void last() {
        index = list.size() - 1;
        tblDanhSach.setRowSelectionInterval(index, index);
        showTable(index);
    }

    public void next() {
        if (index == list.size() - 1) {
            First();
        } else {
            index++;
        }
        tblDanhSach.setRowSelectionInterval(index, index);
        showTable(index);
    }

    public void prev() {
        if (index == 0) {
            last();
        } else {
            index--;
        }
        tblDanhSach.setRowSelectionInterval(index, index);
        showTable(index);
    }

    public void loadDataToArray1() {
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM SinhVien";
            ResultSet rs = stmt.executeQuery(SQL);
            lisst.clear();

            while (rs.next()) {
                String masv = rs.getString(1);
                String tensv = rs.getString(2);
                boolean gt = rs.getBoolean(3);
                String diachi = rs.getString(4);
                String email = rs.getString(5);
                String sodt = rs.getString(6);
                String mal = rs.getString(8);
                String anh = rs.getString(7);
                SinhVien sv = new SinhVien(masv, tensv, gt, diachi, email, sodt, mal, anh);
                lisst.add(sv);
            }
        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtMasv = new javax.swing.JTextField();
        txtTensv = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        txtMaHD = new javax.swing.JTextField();
        txtSoTienCanDong = new javax.swing.JTextField();
        txtThoiGian = new javax.swing.JTextField();
        btnNew1 = new javax.swing.JButton();
        btnSave1 = new javax.swing.JButton();
        btnUpdate1 = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setBackground(new java.awt.Color(255, 204, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HỌC PHÍ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(247, 247, 247)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setText("Tên sinh viên:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setText("Mã sinh viên:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setText("Mã khoá học:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel11.setText("Mã hoá đơn:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel12.setText("Số tiền cần đống:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel13.setText("Thời gian:");

        txtMasv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMasvFocusLost(evt);
            }
        });

        txtTensv.setEditable(false);
        txtTensv.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtTensv.setForeground(new java.awt.Color(255, 0, 0));

        btnNew1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/add-icon.png"))); // NOI18N
        btnNew1.setText("New");
        btnNew1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNew1ActionPerformed(evt);
            }
        });

        btnSave1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/save-icon.png"))); // NOI18N
        btnSave1.setText("Save");
        btnSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave1ActionPerformed(evt);
            }
        });

        btnUpdate1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/update-icon.png"))); // NOI18N
        btnUpdate1.setText("Update");
        btnUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate1ActionPerformed(evt);
            }
        });

        btnDelete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/delete-icon.png"))); // NOI18N
        btnDelete1.setText("Delete");
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtThoiGian, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                        .addComponent(txtSoTienCanDong))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(txtMaKH)
                            .addComponent(txtTensv, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMasv, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate1)
                            .addComponent(btnSave1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(52, 52, 52))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMasv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTensv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoTienCanDong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnNew1)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave1)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate1)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete1)))
                .addGap(26, 26, 26)
                .addComponent(jButton1)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        tabs.addTab("Cập nhật", jPanel2);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));

        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã sinh viên", "Tên sinh viên", "Mã khoá học", "Mã hoá đơn", "Số tiền cần đống", "thời gian"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSach);

        jButton9.setBackground(new java.awt.Color(204, 255, 204));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/First-icon.png"))); // NOI18N
        jButton9.setBorderPainted(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(204, 255, 204));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Previous-icon.png"))); // NOI18N
        jButton10.setBorderPainted(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(204, 255, 204));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Next-icon.png"))); // NOI18N
        jButton11.setBorderPainted(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(204, 255, 204));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Last-icon.png"))); // NOI18N
        jButton12.setBorderPainted(false);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel14.setText("Tìm kiếm:");

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/icons8-search-32.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jButton9)
                .addGap(39, 39, 39)
                .addComponent(jButton10)
                .addGap(55, 55, 55)
                .addComponent(jButton11)
                .addGap(50, 50, 50)
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(btnExit)
                .addGap(35, 35, 35))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExit)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnSearch)
                                .addGap(27, 27, 27))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton10)
                            .addComponent(jButton9)
                            .addComponent(jButton11)
                            .addComponent(jButton12))))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        tabs.addTab("Danh sách", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNew1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNew1ActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnNew1ActionPerformed

    private void btnSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave1ActionPerformed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_btnSave1ActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        MainForm main = new MainForm();
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        First();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        MainForm main = new MainForm();
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate1ActionPerformed
        // TODO add your handling code here:
        updata();
    }//GEN-LAST:event_btnUpdate1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.index = tblDanhSach.getSelectedRow();
            showTable(index);
            tabs.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void txtMasvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMasvFocusLost
        // TODO add your handling code here:
        if (!lisst.isEmpty()) {
            for (SinhVien sv : lisst) {
                if (sv.getMaSV().equalsIgnoreCase(txtMasv.getText())) {
                    txtTensv.setText(sv.getTenSV());
                }
            }
        }
    }//GEN-LAST:event_txtMasvFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HocPhi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HocPhi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HocPhi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HocPhi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HocPhi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnNew1;
    private javax.swing.JButton btnSave1;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMasv;
    private javax.swing.JTextField txtSoTienCanDong;
    private javax.swing.JTextField txtTensv;
    private javax.swing.JTextField txtThoiGian;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
