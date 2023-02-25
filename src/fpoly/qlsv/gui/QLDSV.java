/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fpoly.qlsv.gui;

import fpoly.qlsv.entity.DiemSV;
import fpoly.qlsv.entity.SinhVien;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hoang
 */
public class QLDSV extends javax.swing.JFrame {

    DefaultTableModel tblModel;
    private List<DiemSV> list = new ArrayList<>();
    private int current = 0;
    String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QLGD;user=sa;password=My27012003@";

    public QLDSV() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Quản lý điểm");
        initTable();
        loaddataToCombobox();
        loadDataToArray();
        fillTable();
    }

    public void Display(int i) {
        DiemSV dsv = list.get(i);
        txtMaSV.setText(dsv.getMaSV());
        txtHoTen.setText(dsv.getHoTen());
        cboTenMon.setSelectedItem(dsv.getTenMon());
        txtDiemLab.setText(String.valueOf(dsv.getLab()));
        txtDiemAsm.setText(String.valueOf(dsv.getAssment()));
        txtDiemQuiz.setText(String.valueOf(dsv.getQuiz()));
        txtDiemTB.setText(String.format("%.2f", dsv.getDiemTB()));
    }

    public void loaddataToCombobox() {

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String sql = "Select TenMon from MonHoc";
//            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            list.clear();
            while (rs.next()) {
                cboTenMon.addItem(rs.getString(1));

            }

        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void loadDataToArray() {
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String sql = " select ID, SinhVien.MaSV, TenSV,TenMon,Lab, Assment,Quiz  from Diem join SinhVien on SinhVien.MaSV = Diem.MaSV ";
//            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            list.clear();
            while (rs.next()) {
                int ID = rs.getInt(1);
                String masv = rs.getString(2);
                String hoten = rs.getString(3);
                String tenMon = rs.getString(4);
                double lab = rs.getDouble(5);
                double ASM = rs.getDouble(6);
                double quiz = rs.getDouble(7);
                double diemtb = (lab + ASM + quiz) / 3;
                DiemSV dsv = new DiemSV(ID, masv, hoten, tenMon, lab, ASM, quiz, diemtb);
                list.add(dsv);
            }

        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initTable() {
        tblModel = (DefaultTableModel) tblListDiem.getModel();
        String[] nav = new String[]{"STT", "Mã Sinh Viên", "Họ và Tên", "Tên Môn", "Điểm Lab", "Điểm ASM", "Điểm Quiz", "Điểm TB"};
        tblModel.setColumnIdentifiers(nav);
    }

    public void fillTable() {
        tblModel.setRowCount(0);
        for (DiemSV dsv : list) {
            Object[] row = new Object[]{dsv.getId(), dsv.getMaSV(), dsv.getHoTen(), dsv.getTenMon(), dsv.getLab(), dsv.getAssment(), dsv.getQuiz(), String.format("%.2f", dsv.getDiemTB())};
            tblModel.addRow(row);
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
//        for (DiemSV sv : list) {
//            if (sv.getMaSV().equals(txtMaSV.getText())) {
//                JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!");
//                return false;
//            }
//        }
//        if (txtName.getText().equals("")) {
//            JOptionPane.showMessageDialog(this, "Chưa nhập Họ tên");
//            txtName.setBackground(Color.YELLOW);
//            return false;
//        } else {
//            txtName.setBackground(Color.WHITE);
//        }
        if (txtDiemLab.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập điểm");
            return false;
        }
        if (Double.parseDouble(txtDiemLab.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Điểm phải lớn hơn 0");
            return false;
        }
        if (Double.parseDouble(txtDiemLab.getText()) > 10) {
            JOptionPane.showMessageDialog(this, "Điểm phải nhỏ hơn hoặc bằng 10");
            return false;
        }
        try {
            Double.parseDouble(txtDiemLab.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là số", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtDiemAsm.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập điểm");
            return false;
        }
        if (Double.parseDouble(txtDiemAsm.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Điểm phải lớn hơn 0");
            return false;
        }
        if (Double.parseDouble(txtDiemAsm.getText()) > 10) {
            JOptionPane.showMessageDialog(this, "Điểm phải nhỏ hơn hoặc bằng 10");
            return false;
        }
        try {
            Double.parseDouble(txtDiemAsm.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là số", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtDiemQuiz.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập điểm");
            return false;
        }
        if (Double.parseDouble(txtDiemQuiz.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Điểm phải lớn hơn 0");
            return false;
        }
        if (Double.parseDouble(txtDiemQuiz.getText()) > 10) {
            JOptionPane.showMessageDialog(this, "Điểm phải nhỏ hơn hoặc bằng 10");
            return false;
        }
        try {
            Double.parseDouble(txtDiemQuiz.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là số", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void updateInfo() {
        tblListDiem.setRowSelectionInterval(current, current);
//        Display(current);
    }

    public void Save() {
        if (CheckForm()) {
            try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
                String SQL = "Insert into Diem values ?,?,?,?,?";
                PreparedStatement st = con.prepareStatement(SQL);
                st.setString(1, txtMaSV.getText());
                st.setString(2, cboTenMon.getSelectedItem().toString().trim());
                st.setDouble(3, Double.parseDouble(txtDiemLab.getText()));
                st.setDouble(4, Double.parseDouble(txtDiemAsm.getText()));
                st.setDouble(5, Double.parseDouble(txtDiemQuiz.getText()));
                st.executeUpdate();
//                JOptionPane.showMessageDialog(this, "Save thành công");
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
            String SQL = "update Diem set TenMon = ?, Lab = ?, Assment =?, Quiz = ? where MaSV = ?";
            PreparedStatement st = con.prepareStatement(SQL);
//            st.setString(1, txtMaMon.getText());
            st.setString(2, txtDiemLab.getText());
            st.setString(3, txtDiemAsm.getText());
            st.setString(4, txtDiemQuiz.getText());
            st.setString(5, txtMaSV.getText());
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
            String SQL = "delete from Diem where MaSV = ?";
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, txtMaSV.getText());
            st.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            con.close();
            loadDataToArray();
//            Display(current--);
            fillTable();

        } // Handle any errors that may have occurred.
        catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Error");
        }
    }

    public void xoaForm() {
        txtMaSV.setText("");
        txtHoTen.setText("");
        cboTenMon.setSelectedIndex(0);
        txtDiemLab.setText("");
        txtDiemAsm.setText("");
        txtDiemQuiz.setText("");
        txtDiemTB.setText("");
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
        jToolBar4 = new javax.swing.JToolBar();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtDiemLab = new javax.swing.JTextField();
        txtMaSV = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtDiemQuiz = new javax.swing.JTextField();
        txtDiemAsm = new javax.swing.JTextField();
        txtDiemTB = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        cboTenMon = new javax.swing.JComboBox<>();
        jToolBar5 = new javax.swing.JToolBar();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListDiem = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        txtMaSV2 = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setBackground(new java.awt.Color(255, 204, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÝ ĐIỂM");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(214, 214, 214)
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

        jToolBar4.setRollover(true);

        jPanel5.setBackground(new java.awt.Color(204, 255, 204));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Họ và Tên:");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Mã Sinh Viên:");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Điểm ASM:");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Tên Môn:");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Điểm Lab:");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Điểm TB:");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("Điểm Quiz:");

        txtMaSV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaSVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaSVFocusLost(evt);
            }
        });

        txtHoTen.setEditable(false);
        txtHoTen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHoTenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoTenFocusLost(evt);
            }
        });

        txtDiemQuiz.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiemQuizFocusLost(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(204, 255, 204));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/delete-icon.png"))); // NOI18N
        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
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

        cboTenMon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn Môn Học" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(btnFirst)
                .addGap(24, 24, 24)
                .addComponent(btnPrev)
                .addGap(18, 18, 18)
                .addComponent(btnNext)
                .addGap(18, 18, 18)
                .addComponent(btnLast)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaSV, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(txtHoTen)
                    .addComponent(txtDiemLab)
                    .addComponent(txtDiemQuiz)
                    .addComponent(txtDiemTB, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(txtDiemAsm, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                    .addComponent(cboTenMon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiemLab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiemAsm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiemQuiz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiemTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrev, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnFirst, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNext, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLast, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(31, 31, 31))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar4.add(jPanel5);

        tabs.addTab("Cập Nhật", jToolBar4);

        jToolBar5.setRollover(true);

        jPanel6.setBackground(new java.awt.Color(204, 255, 204));

        tblListDiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sinh Viên", "Họ và Tên", "Mã Môn", "Điểm Lab", "Điêm ASM", "Điểm Quiz", "Điểm TB"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListDiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListDiemMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblListDiem);

        jPanel7.setBackground(new java.awt.Color(204, 255, 204));

        jButton37.setBackground(new java.awt.Color(204, 255, 204));
        jButton37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/First-icon.png"))); // NOI18N
        jButton37.setBorderPainted(false);

        jButton38.setBackground(new java.awt.Color(204, 255, 204));
        jButton38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Previous-icon.png"))); // NOI18N
        jButton38.setBorderPainted(false);

        jButton39.setBackground(new java.awt.Color(204, 255, 204));
        jButton39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Next-icon.png"))); // NOI18N
        jButton39.setBorderPainted(false);

        jButton40.setBackground(new java.awt.Color(204, 255, 204));
        jButton40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/Last-icon.png"))); // NOI18N
        jButton40.setBorderPainted(false);

        jButton41.setText("EXIT");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jButton37)
                .addGap(38, 38, 38)
                .addComponent(jButton38)
                .addGap(37, 37, 37)
                .addComponent(jButton39)
                .addGap(33, 33, 33)
                .addComponent(jButton40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton41)
                .addGap(15, 15, 15))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton39)
                    .addComponent(jButton40)
                    .addComponent(jButton38, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton37)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButton41)))
                .addGap(15, 15, 15))
        );

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/icons8-search-32.png"))); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtMaSV2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSV2))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar5.add(jPanel6);

        tabs.addTab("Danh Sách", jToolBar5);

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
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void txtMaSVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaSVFocusLost
        // TODO add your handling code here:

        if (!list.isEmpty()) {
            for (DiemSV sv : list) {
                if (sv.getMaSV().equalsIgnoreCase(txtMaSV.getText())) {
                    txtHoTen.setText(sv.getHoTen());
                }
            }
        }

    }//GEN-LAST:event_txtMaSVFocusLost

    private void txtDiemQuizFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiemQuizFocusLost
        // TODO add your handling code here:
        if (txtDiemLab.getText().equals("") || txtDiemAsm.getText().equals("") || txtDiemQuiz.getText().equals("")) {
            return;
        }
        double lab = Double.parseDouble(txtDiemLab.getText());
        double asm = Double.parseDouble(txtDiemAsm.getText());
        double quiz = Double.parseDouble(txtDiemQuiz.getText());
        double avg = (lab + asm + quiz) / 3;
        String st = String.format("%.2f", avg);
        txtDiemTB.setText(st + "");
    }//GEN-LAST:event_txtDiemQuizFocusLost

    private void tblListDiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListDiemMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.current = tblListDiem.getSelectedRow();
            Display(current);
            tabs.setSelectedIndex(0);
            updateInfo();
        }
    }//GEN-LAST:event_tblListDiemMouseClicked

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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        this.Save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        if (txtMaSV2.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã sinh viên!");
            txtMaSV2.requestFocus();
//        } else if (!list.isEmpty() || current >= 0) {
//            for (DiemSV dsv : list) {
//                if (dsv.getMaSV().equalsIgnoreCase(txtMaSV2.getText())) {
////                    txtMaSV.setText(dsv.getMaSV());
////                    txtHoTen.setText(dsv.getHoTen());
//////                    txtMaMon.setText(dsv.getMaMon());
////                    txtDiemLab.setText(String.valueOf(dsv.getLab()));
////                    txtDiemAsm.setText(String.valueOf(dsv.getAssment()));
////                    txtDiemQuiz.setText(String.valueOf(dsv.getQuiz()));
////                    txtDiemTB.setText(String.format("%.2f", dsv.getDiemTB()));
//////                    tabs.setSelectedIndex(0);
////                    return;
//                        fillTable();
//                        updateInfo();
//                        tblModel.fireTableDataChanged(); 
//                        return;
//                }
//            }
//        }
        } else {
            try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
                String SQL = "select ID,Diem.MaSV, TenSV, Diem.TenMon, Lab,Assment,Quiz,(Lab + Assment + Quiz)/3 as 'DiemTB'  from Diem INNER JOIN SinhVien on Diem.MaSV = SinhVien.MaSV where Diem.MaSV like ?";
                PreparedStatement pr = con.prepareStatement(SQL);

                String search = "%";
                if (!txtMaSV2.getText().equals("")) {
                    search += txtMaSV2.getText() + "%";
                }
                pr.setString(1, search);

                ResultSet rs = pr.executeQuery();
                tblModel.setRowCount(0);

                while (rs.next()) {
                    tblModel.addRow(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("MaSV"),
                        rs.getString("TenSV"),
                        rs.getString("TenMon"),
                        rs.getDouble("Lab"),
                        rs.getDouble("Assment"),
                        rs.getDouble("Quiz"),
                        rs.getDouble("DiemTB")
                    });
                }

                tblModel.fireTableDataChanged();

            } // Handle any errors that may have occurred.
            catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage());
            }

        }
        if (txtMaSV2.getText().equalsIgnoreCase("")) {
            fillTable();
        }
        txtMaSV2.setText("");
//        if (!txtMaSV2.getText().equals("")) {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên!");
//            txtMaSV2.requestFocus();
//        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtHoTenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoTenFocusLost

    private void txtHoTenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoTenFocusGained

    private void txtMaSVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaSVFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaSVFocusGained

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
            java.util.logging.Logger.getLogger(QLDSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLDSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLDSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLDSV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLDSV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboTenMon;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JToolBar jToolBar5;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblListDiem;
    private javax.swing.JTextField txtDiemAsm;
    private javax.swing.JTextField txtDiemLab;
    private javax.swing.JTextField txtDiemQuiz;
    private javax.swing.JTextField txtDiemTB;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtMaSV2;
    // End of variables declaration//GEN-END:variables
}
