/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fpoly.qlsv.gui;

import fpoly.qlsv.entity.SinhVien;
import fpoly.qlsv.entity.TotNghiep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hoang
 */
public class QLTN extends javax.swing.JFrame {

    DefaultTableModel tblModel;
    private List<TotNghiep> list = new ArrayList<>();
    String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=QLGD;user=sa;password=My27012003@;encrypt=true;trustServerCertificate=true";
    private int index = -1;

    public QLTN() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public void initTable() {
        txtTimKiem.setEditable(true);
        tblModel = (DefaultTableModel) tblTotNghiep.getModel();
        String[] nav = new String[]{"Mã Sinh Viên", "Họ và Tên", "Ngành Học", "Điểm Trung Bình", "Xếp loại"};
        tblModel.setColumnIdentifiers(nav);
    }

    public void fillToTable() {
        DefaultTableModel tblModel = (DefaultTableModel) tblTotNghiep.getModel();
        tblModel.setRowCount(0);
        for (TotNghiep t : list) {
            Object[] row = new Object[]{t.getMaSV(), t.getHoTen(), t.getNganh(), t.getDiemTB(), t.getXepLoai()};
            tblModel.addRow(row);
        }
    }

    public void loadDataToArray() {
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "Select tn.MaSV, sv.TenSV, tn.MaNganh, tn.XepLoai\n"
                    + "From TotNghiep tn, SinhVien sv";;
            ResultSet rs = stmt.executeQuery(SQL);
            list.clear();

            while (rs.next()) {
                String masv = rs.getString(1);
                String hoten = rs.getString(2);
                String Nganh = rs.getString(3);
                double diemtb = rs.getDouble(4);
                String xeploai = rs.getString(5);
                TotNghiep tn = new TotNghiep(masv, hoten, Nganh, diemtb, xeploai);
                list.add(tn);
            }
        } // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showTable(int index) {
        txtMaSV.setText(list.get(index).getMaSV());
        txtNganh.setText(list.get(index).getNganh());
        txtXepLoai.setText(list.get(index).getXepLoai());
    }

    public boolean validateForm() {
        if (txtMaSV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã sinh viên!");
            txtMaSV.requestFocus();
            return false;
        }

        for (TotNghiep tn : list) {
            if (tn.getMaSV().equals(txtMaSV.getText())) {
                JOptionPane.showMessageDialog(this, "Mã sinh viên đã tồn tại!");
                return false;
            }
        }
        if (txtNganh.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa nhập ngành!");
            txtNganh.requestFocus();
            return false;
        }
        if (txtXepLoai.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa nhập xếp loại!");
            txtXepLoai.requestFocus();
            return false;
        }
        return true;
    }

    public void newForm() {
        txtMaSV.setText("");
        txtNganh.setText("");
        txtXepLoai.setText("");
    }

    public void save() {
        if (validateForm()) {
            try (java.sql.Connection con = DriverManager.getConnection(connectionUrl);) {
                String sql = "Insert Into TotNghiep(MaSV,MaNganh,XepLoai) Values (?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, txtMaSV.getText());
                ps.setString(2, txtNganh.getText());
                ps.setString(3, txtXepLoai.getText());

                int kq = ps.executeUpdate();
                if (kq == 1) {
                    JOptionPane.showMessageDialog(this, "Lưu thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Lưu thất bại!");
                }
                ps.close();
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void delete() {
        if (!txtMaSV.getText().isEmpty()) {
            try (java.sql.Connection con = DriverManager.getConnection(connectionUrl);) {
                String sql = "Delete TotNghiep where MaSV = ?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, txtMaSV.getText());
                int kq = ps.executeUpdate();
                if (kq == 1) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
                ps.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (validateForm()) {
            try (java.sql.Connection con = DriverManager.getConnection(connectionUrl);) {
                String sql = "Update TotNghiep Set MaSV = ?, MaNganh =?, XepLoai = ? where MaSV = ?";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, txtMaSV.getText());
                ps.setString(2, txtNganh.getText());
                ps.setString(3, txtXepLoai.getText());

                int kq = ps.executeUpdate();
                if (kq == 1) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
                ps.close();
                con.close();
                loadDataToArray();

            } catch (Exception e) {
            }
        }
    }

    public void search() {
        try (java.sql.Connection con = DriverManager.getConnection(connectionUrl);) {
            String sql = "SELECT TotNgiep.MaSV,MaNganh,XepLoai"
                    + "FROM TotNghiep WHERE MaSV = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtMaSV.getText());
            ResultSet rs = ps.executeQuery();
            list.clear();
            while (rs.next()) {
                TotNghiep totnghiep = new TotNghiep();
                //gan du lieu vao
                totnghiep.setMaSV(rs.getString("MaSV"));
                totnghiep.setNganh(rs.getString("MaNganh"));
                totnghiep.setXepLoai(rs.getString("XepLoai"));
                //them diem vao danh sach list
                list.add(totnghiep);
            }
            //hien thi len bang diem
            //lay mo hinh du lieu va xoa sach cac bang
            DefaultTableModel tblModel = (DefaultTableModel) tblTotNghiep.getModel();
            tblModel.setRowCount(0);
            //duyet qua danh sach list, lay tung dong them vao table
            for (TotNghiep tn : list) {
                Object[] row = new Object[]{tn.getMaSV(), tn.getNganh(), tn.getXepLoai()};
                tblModel.addRow(row);
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void first() {
        index = 0;
        tblTotNghiep.setRowSelectionInterval(index, index);
        showTable(index);
    }

    public void last() {
        index = list.size() - 1;
        tblTotNghiep.setRowSelectionInterval(index, index);
        showTable(index);
    }

    public void next() {
        if (index == list.size() - 1) {
            first();
        } else {
            index++;
        }
        tblTotNghiep.setRowSelectionInterval(index, index);
        showTable(index);
    }

    public void prev() {
        if (index == 0) {
            last();
        } else {
            index--;
        }
        tblTotNghiep.setRowSelectionInterval(index, index);
        showTable(index);
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
        jPanel1 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        txtMaSV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNganh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtXepLoai = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTotNghiep = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setBackground(new java.awt.Color(255, 204, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TỐT NGHIỆP");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        txtTimKiem.setText("Nhập mã sv để tìm kiếm");

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/icons8-search-32.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("MSSV:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Ngành:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Xếp loại:");

        tblTotNghiep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Sinh Viên", "Họ và Tên", "Ngành Học", "Điểm Trung Bình", "Xếp Loại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTotNghiep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTotNghiepMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTotNghiep);

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/add-icon.png"))); // NOI18N
        btnThem.setText("New");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/update-icon.png"))); // NOI18N
        btnSua.setText("Update");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/delete-icon.png"))); // NOI18N
        btnXoa.setText("Delete");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fpoly/qlsv/icon/save-icon.png"))); // NOI18N
        btnLuu.setText("Save");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
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

        jButton1.setText("EXIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(btnFirst)
                        .addGap(42, 42, 42)
                        .addComponent(btnPrev)
                        .addGap(37, 37, 37)
                        .addComponent(btnNext)
                        .addGap(39, 39, 39)
                        .addComponent(btnLast)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(btnTimKiem))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnThem)
                                        .addGap(14, 14, 14)
                                        .addComponent(btnSua)
                                        .addGap(11, 11, 11)
                                        .addComponent(btnLuu))
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtNganh, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(btnXoa))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtMaSV, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                                    .addComponent(txtXepLoai))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtXepLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jButton1)
                        .addGap(0, 33, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnNext, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnPrev, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnFirst))
                            .addComponent(btnLast, javax.swing.GroupLayout.Alignment.TRAILING)))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblTotNghiepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTotNghiepMouseClicked
        index = tblTotNghiep.getSelectedRow();
        showTable(index);
    }//GEN-LAST:event_tblTotNghiepMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        newForm();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
        newForm();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        try {
            save();
            loadDataToArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        try {
            first();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        try {
            prev();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        try {
            next();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        try {
            last();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnLastActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(QLTN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLTN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLTN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLTN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLTN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTotNghiep;
    private javax.swing.JTextField txtMaSV;
    private javax.swing.JTextField txtNganh;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtXepLoai;
    // End of variables declaration//GEN-END:variables
}
