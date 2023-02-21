/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fpoly.qlsv.gui;

import fpoly.qlsv.entity.SinhVien;
import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hoang
 */
public class QLTTSV extends javax.swing.JFrame {

    DefaultTableModel tblModel;
    private List<SinhVien> list = new ArrayList<>();
    private int current = 0;
    String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QLGD;user=sa;password=My27012003@";
    String nameFile = "";

    private static final String P_EMAIL = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";

    public QLTTSV() {
        initComponents();
        setLocationRelativeTo(null);
        initTable();
        loadDataToArray();
//        Display(current);
        fillTable();
        lblBanGhi.setText(ThongTinBanGhi());
    }

    public void Display(int i) {
        SinhVien sv = list.get(i);
        txtMaSV.setText(sv.getMaSV());
        txtName.setText(sv.getTenSV());
        boolean gt = sv.isGioiTinh();
        if (gt == true) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        txtDiaChi.setText(sv.getDiaChi());
        txtEmail.setText(sv.getEmail());
        txtSoDT.setText(sv.getSđt());
        txtMaLop.setText(sv.getMaL());
        upImage(sv.getAnh());

    }

    public void loadDataToArray() {
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM SinhVien";
            ResultSet rs = stmt.executeQuery(SQL);
            list.clear();

            while (rs.next()) {
                String masv = rs.getString(1);
                String tensv = rs.getString(2);
                boolean gt = rs.getBoolean(3);
                String diachi = rs.getString(4);
                String email = rs.getString(5);
                String sodt = rs.getString(6);
                String mal = rs.getString(7);
                String anh = rs.getString(8);
                SinhVien sv = new SinhVien(masv, tensv, gt, diachi, email, sodt, mal, anh);
                list.add(sv);
            }
        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initTable() {
        tblModel = (DefaultTableModel) tblListDSSV.getModel();
        String[] nav = new String[]{"MÃ SV", "HỌ VÀ TÊN", "Giới Tính", "Địa Chỉ", "Email", "Số ĐT", "Mã Lớp", "Ảnh"};
        tblModel.setColumnIdentifiers(nav);
    }

    public void upImage(String anh) {
        ImageIcon icon = new ImageIcon("src\\fpoly\\qlsv\\Image" + anh);
        Image image = icon.getImage();
        ImageIcon icon1 = new ImageIcon(image.getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), image.SCALE_SMOOTH));
        lblAnh.setIcon(icon1);
    }

    private void updateInfo() {
        tblListDSSV.setRowSelectionInterval(current, current);
        Display(current);
        lblBanGhi.setText(ThongTinBanGhi());
    }

    public void fillTable() {
        tblModel.setRowCount(0);
        for (SinhVien sv : list) {
            Object[] low = new Object[]{sv.getMaSV(), sv.getTenSV(), (sv.isGioiTinh() ? "Nam" : "Nữ"), sv.getDiaChi(), sv.getEmail(), sv.getSđt(), sv.getMaL(), sv.getAnh()};
            tblModel.addRow(low);
        }
    }

    public boolean CheckForm() {
        if (txtMaSV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã sinh viên");
            txtMaSV.setBackground(Color.YELLOW);
            return false;
        } else {
            txtMaSV.setBackground(Color.WHITE);
        }
        for (SinhVien sv : list) {
            if (sv.getMaSV().equals(txtMaSV.getText())) {
                JOptionPane.showMessageDialog(this, "Mã sinh viên đã tồn tại!");
                return false;
            }
        }
        if (txtName.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập Họ tên");
            txtName.setBackground(Color.YELLOW);
            return false;
        } else {
            txtName.setBackground(Color.WHITE);
        }
        if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập email");
            txtEmail.setBackground(Color.YELLOW);
            return false;
        } else {
            txtEmail.setBackground(Color.WHITE);
        }
        Matcher matcher = Pattern.compile(P_EMAIL).matcher(txtEmail.getText());
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(this, "Email sai định dạng");
            txtEmail.setBackground(Color.YELLOW);
            return false;
        } else {
            txtEmail.setBackground(Color.WHITE);
        }
        if (txtSoDT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập số điện thoại");
            txtSoDT.setBackground(Color.YELLOW);
            return false;
        } else {
            txtSoDT.setBackground(Color.WHITE);
        }
        if (rdoNam.isSelected() == false && rdoNu.isSelected() == false) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn giới tính");
            return false;
        }
        if (txtDiaChi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập địa chỉ");
            txtDiaChi.setBackground(Color.YELLOW);
            return false;
        } else {
            txtDiaChi.setBackground(Color.WHITE);
        }
        return true;
    }
//

    public void xoaForm() {
        txtMaSV.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtSoDT.setText("");
        buttonGroup1.clearSelection();
        txtDiaChi.setText("");
        txtMaLop.setText("");
        lblAnh.setIcon(null);
    }

    public void Save() {
        if (CheckForm()) {
            try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
                String SQL = "insert into SinhVien values(?,?,?,?,?,?,?,?)";
                PreparedStatement st = con.prepareStatement(SQL);
                st.setString(1, txtMaSV.getText());
                st.setString(2, txtName.getText());
                boolean gt;
                if (rdoNam.isSelected()) {
                    gt = true;
                } else {
                    gt = false;
                }
                st.setBoolean(3, gt);
                st.setString(4, txtDiaChi.getText());
                st.setString(5, txtEmail.getText());
                st.setString(6, txtSoDT.getText());
                st.setString(7, txtMaLop.getText());
                st.setString(8, nameFile);
                st.executeUpdate();
                JOptionPane.showMessageDialog(this, "Save thành công");
                con.close();
                loadDataToArray();
                xoaForm();
                fillTable();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Error");
            }
        }
    }

    public void Update() {
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "update SinhVien set TenSV = ?, GioiTinh = ?, DiaChi = ?, Email = ?, Sđt =?, MaL = ?, Anh = ? where MaSV = ?";
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, txtName.getText());
            boolean gt;
            if (rdoNam.isSelected()) {
                gt = true;
            } else {
                gt = false;
            }
            st.setBoolean(2, gt);
            st.setString(3, txtDiaChi.getText());
            st.setString(4, txtEmail.getText());
            st.setString(5, txtSoDT.getText());
            st.setString(6, txtMaLop.getText());
            st.setString(7, nameFile);
            st.setString(8, txtMaSV.getText());
            st.executeUpdate();
            JOptionPane.showMessageDialog(this, "Update thành công");
            con.close();
            loadDataToArray();
            fillTable();
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Error");
        }
    }

    public void Delete() {
        if (txtMaSV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã sv");
            txtMaSV.requestFocus();
            return;
        }
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "delete SinhVien where MaSV = ?";
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, txtMaSV.getText());
            st.execute();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            con.close();
            loadDataToArray();
            fillTable();
            Display(current--);

        } // Handle any errors that may have occurred.
        catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Error");
        }
    }

    public String ThongTinBanGhi() {
        return "Record: " + (current + 1) + " of " + list.size();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tabs = new javax.swing.JTabbedPane();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        txtMaSV = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtSoDT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtMaLop = new javax.swing.JTextField();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        btnDelete = new javax.swing.JButton();
        btnChonAnh = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblBanGhi = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListDSSV = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        txtMaSV2 = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Họ và Tên:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Mã Sinh Viên:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Email:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Giới Tính:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Địa Chỉ:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Mã Lớp:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Số ĐT:");

        rdoNam.setBackground(new java.awt.Color(204, 255, 204));
        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(204, 255, 204));
        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        btnDelete.setBackground(new java.awt.Color(204, 255, 204));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/delete-icon.png"))); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnChonAnh.setText("Chọn Ảnh");
        btnChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonAnhActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(204, 255, 204));
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/add-icon.png"))); // NOI18N
        btnNew.setText("NEW");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(204, 255, 204));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/save-icon.png"))); // NOI18N
        btnSave.setText("SAVE");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(204, 255, 204));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/update-icon.png"))); // NOI18N
        btnUpdate.setText("UPDATE");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnFirst.setBackground(new java.awt.Color(204, 255, 204));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/First-icon.png"))); // NOI18N
        btnFirst.setBorderPainted(false);
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(204, 255, 204));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Previous-icon.png"))); // NOI18N
        btnPrev.setBorderPainted(false);
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(204, 255, 204));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Next-icon.png"))); // NOI18N
        btnNext.setBorderPainted(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(204, 255, 204));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Last-icon.png"))); // NOI18N
        btnLast.setBorderPainted(false);
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        lblBanGhi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblBanGhi.setForeground(new java.awt.Color(255, 0, 51));
        lblBanGhi.setText("Record: 1 of 10");

        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/avatar-icon.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
        );

        lblAnh.getAccessibleContext().setAccessibleParent(this);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaLop, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)
                                .addComponent(btnChonAnh)))
                        .addContainerGap(120, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtMaSV, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                                .addComponent(txtName)
                                .addComponent(txtDiaChi)
                                .addComponent(txtSoDT))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(18, 18, 18)
                                .addComponent(rdoNu)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdate))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnDelete)
                                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(98, 98, 98))))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(btnFirst)
                .addGap(18, 18, 18)
                .addComponent(btnPrev)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNext)
                        .addGap(18, 18, 18)
                        .addComponent(btnLast))
                    .addComponent(lblBanGhi, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChonAnh, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaLop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblBanGhi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnFirst)
                            .addComponent(btnPrev)
                            .addComponent(btnNext)
                            .addComponent(btnLast))
                        .addGap(44, 44, 44))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jToolBar1.add(jPanel1);

        tabs.addTab("Cập Nhật", jToolBar1);

        jToolBar2.setRollover(true);

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        tblListDSSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sinh Viên", "Họ và Tên", "Giới Tính", "Địa Chỉ", "Email", "Số ĐT", "Mã Lớp", "Ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListDSSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListDSSVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblListDSSV);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));

        jButton10.setBackground(new java.awt.Color(204, 255, 204));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/First-icon.png"))); // NOI18N
        jButton10.setBorderPainted(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(204, 255, 204));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Previous-icon.png"))); // NOI18N
        jButton11.setBorderPainted(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(204, 255, 204));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Next-icon.png"))); // NOI18N
        jButton12.setBorderPainted(false);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(204, 255, 204));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Last-icon.png"))); // NOI18N
        jButton13.setBorderPainted(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton15.setText("EXIT");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jButton10)
                .addGap(38, 38, 38)
                .addComponent(jButton11)
                .addGap(37, 37, 37)
                .addComponent(jButton12)
                .addGap(33, 33, 33)
                .addComponent(jButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton15)
                .addGap(15, 15, 15))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton12)
                    .addComponent(jButton13)
                    .addComponent(jButton11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton10)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButton15)))
                .addGap(15, 15, 15))
        );

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/icons8-search-32.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtMaSV2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSV2))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar2.add(jPanel2);

        tabs.addTab("Danh Sách", jToolBar2);

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setBackground(new java.awt.Color(255, 204, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ THÔNG TIN SINH VIÊN");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(155, 155, 155))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        this.Save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        this.xoaForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        this.Update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        this.Delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        current = 0;
        Display(current);
        updateInfo();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        current--;
        if (current < 0) {
//            JOptionPane.showMessageDialog(this, "Đang ở đầu danh sách");
//            return;
            btnLastActionPerformed(evt);
        }
        Display(current);
        updateInfo();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        current++;
        if (current >= list.size()) {
//            JOptionPane.showMessageDialog(this, "Đang ở cuổi");
//            return;
            btnFirstActionPerformed(evt);
        }
        Display(current);
        updateInfo();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        current = list.size() - 1;
        Display(current);
        updateInfo();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonAnhActionPerformed
        // TODO add your handling code here:
        JFileChooser file = new JFileChooser("src\\fpoly\\qlsv\\Image");
        int kq = file.showOpenDialog(file);
        if (kq == JFileChooser.APPROVE_OPTION) {
            nameFile = file.getSelectedFile().getName();
            upImage(nameFile);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn ảnh...");
        }
    }//GEN-LAST:event_btnChonAnhActionPerformed

    private void tblListDSSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListDSSVMouseClicked
        // TODO add your handling code here:
//        int row = tblListDSSV.getSelectedRow();

        if (evt.getClickCount() == 2) {
            this.current = tblListDSSV.getSelectedRow();
            Display(current);
            tabs.setSelectedIndex(0);
            updateInfo();
        }

    }//GEN-LAST:event_tblListDSSVMouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        current = 0;
        Display(current);
        updateInfo();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        current--;
        if (current < 0) {
//            JOptionPane.showMessageDialog(this, "Đang ở đầu danh sách");
//            return;
            btnLastActionPerformed(evt);
        }
        Display(current);
        updateInfo();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        current++;
        if (current >= list.size()) {
//            JOptionPane.showMessageDialog(this, "Đang ở cuổi");
//            return;
            btnFirstActionPerformed(evt);
        }
        Display(current);
        updateInfo();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        current = list.size() - 1;
        Display(current);
        updateInfo();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        if (txtMaSV2.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã sinh viên!");
            txtMaSV2.requestFocus();
        } else if (!list.isEmpty() || current >= 0) {
            for (SinhVien sv : list) {
                if (sv.getMaSV().equalsIgnoreCase(txtMaSV2.getText())) {
                    txtMaSV.setText(sv.getMaSV());
                    txtName.setText(sv.getTenSV());
                    txtEmail.setText(sv.getEmail());
                    txtSoDT.setText(sv.getSđt());
                    if (sv.isGioiTinh() == true) {
                        rdoNam.setSelected(true);
                    } else {
                        rdoNu.setSelected(true);
                    }
                    txtDiaChi.setText(sv.getDiaChi());
                    txtMaLop.setText(sv.getMaL());
                    upImage(sv.getAnh());
                    tabs.setSelectedIndex(0);
                    return;
                }
            }
        }
        if (!txtMaSV2.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên!");
            txtMaSV2.requestFocus();
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

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
            java.util.logging.Logger.getLogger(QLTTSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLTTSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLTTSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLTTSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLTTSV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonAnh;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblBanGhi;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblListDSSV;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaLop;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtMaSV2;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSoDT;
    // End of variables declaration//GEN-END:variables
}
