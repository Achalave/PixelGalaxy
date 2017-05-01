package devices;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class ParticleControlCenter extends javax.swing.JPanel {

    //Swing GUI
    static JFrame frame;
    static ParticleControlCenter panel;
    //Vars
    final String textPath = "src/resources/controls/";
    final String particlePath = "src/pixelgalaxy/particles/";
    final String reactionPath = "src/pixelgalaxy/reactions/";

    public static void main(String[] args) {
        frame = new JFrame("Reaction Control Panel");
        panel = new ParticleControlCenter();
        frame.setSize(panel.getPreferredSize());
        frame.add(panel);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public ParticleControlCenter() {
        initComponents();
        refreshReactions();
        checkForMissingDocuments();
        refreshParticles();
    }

    public ArrayList<String> getParticles() {
        ArrayList<String> particles = new ArrayList<String>();
        File f = new File(particlePath);
        String[] list = f.list();
        if (list != null) {
            for (String s : list) {
                if (s.indexOf(".java") > 0) {
                    particles.add(convertFileToClass(particlePath + s));
                }
            }
        }
        return particles;
    }

    public ArrayList<String> getReactions() {
        ArrayList<String> reactions = new ArrayList<String>();
        File f = new File(reactionPath);
        String[] list = f.list();
        if (list != null) {
            for (String s : list) {
                if (s.indexOf(".java") > 0) {
                    reactions.add(convertFileToClass(reactionPath + s));
                }
            }
        }
        return reactions;
    }

    public String convertFileToClass(String s) {
        int index = s.indexOf(".java");
        s = s.substring(4, index);
        return s.replaceAll("/", ".");
    }

    public String convertClassToFile(String s) {
        return "src/" + s.replaceAll("\\.", "/") + ".java";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        particle1Selection = new javax.swing.JComboBox();
        particle2Selection = new javax.swing.JComboBox();
        reactionSelection = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        refreshReactions = new javax.swing.JButton();
        refreshParticles = new javax.swing.JButton();
        submit = new javax.swing.JButton();
        newCollision = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        argumentTextBox = new javax.swing.JTextArea();
        bothFiles = new javax.swing.JCheckBox();

        particle1Selection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        particle1Selection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                particle1SelectionActionPerformed(evt);
            }
        });

        particle2Selection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        particle2Selection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                particle2SelectionActionPerformed(evt);
            }
        });

        reactionSelection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        reactionSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reactionSelectionActionPerformed(evt);
            }
        });
        reactionSelection.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                reactionSelectionPropertyChange(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Particle:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Collides With:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Throws Reaction:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("With Arguments:");

        refreshReactions.setText("Refresh");
        refreshReactions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshReactionsActionPerformed(evt);
            }
        });

        refreshParticles.setText("Refresh");
        refreshParticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshParticlesActionPerformed(evt);
            }
        });

        submit.setText("jButton1");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        newCollision.setText("New Collision");
        newCollision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCollisionActionPerformed(evt);
            }
        });

        argumentTextBox.setColumns(20);
        argumentTextBox.setRows(5);
        argumentTextBox.setWrapStyleWord(true);
        argumentTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                argumentTextBoxKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(argumentTextBox);

        bothFiles.setSelected(true);
        bothFiles.setText("Save In Both Files");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, particle2Selection, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(refreshParticles, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(reactionSelection, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(refreshReactions, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(particle1Selection, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(submit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(newCollision, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(bothFiles)
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(particle1Selection, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(refreshParticles)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(particle2Selection, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(newCollision)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(reactionSelection, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(refreshReactions)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 7, Short.MAX_VALUE)
                .add(bothFiles)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(submit)
                .add(18, 18, 18))
        );

        jTabbedPane1.addTab("Manage Reactions", jPanel1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void checkForMissingDocuments() {
        ArrayList<String> docs = getNonExistantTextDocuments();
        if (docs.size() > 0) {
            int ans = JOptionPane.showConfirmDialog(this, "Some particle control documents are missing...\nWould you like to create them now?");
            if (ans == JOptionPane.OK_OPTION) {
                createTextDocuments(docs);
            }
        }
    }

    public ArrayList<String> getNonExistantTextDocuments() {
        ArrayList<String> particles = getParticles();
        ArrayList<String> texts = new ArrayList<String>();
        File f = new File(textPath);
        String[] list = f.list();
        if (list != null) {
            for (String s : list) {
                int index = s.indexOf(".txt");
                if (index > 0) {
                    texts.add(s.substring(0, index));
                }
            }
        }

        //Overlay documents
        particles.removeAll(texts);
        return particles;
    }

    public void createTextDocuments(ArrayList<String> docs) {
        String defaultReaction = (String) JOptionPane.showInputDialog(
                frame,
                "Select A Default Reaction",
                "Create Unmade Control Documents",
                JOptionPane.PLAIN_MESSAGE,
                null,
                getReactions().toArray(),
                "");
        if ((defaultReaction == null) || (defaultReaction.length() < 0)) {
            return;
        }

        for (String s : docs) {
            createNewTextDocument(s, defaultReaction);
        }
    }

    public void createNewTextDocument(String name, String defaultReaction) {
        File f = new File(textPath + name + ".txt");
        try {
            f.createNewFile();
            PrintWriter out = new PrintWriter(f);
            out.print("-" + defaultReaction);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ParticleControlCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //For reaction management
    public void setSelectedParticle(String selection) {
        ArrayList<String> madeReactions = new ArrayList<String>();
        String filePath = textPath + selection + ".txt";
        Scanner in = null;
        try {
            in = new Scanner(new File(filePath));
            while (in.hasNext()) {
                String line = in.nextLine();
                if (line.startsWith("-")) {
                    madeReactions.add("Default:" + line.substring(1));
                } else {
                    madeReactions.add(line);
                    in.nextLine();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParticleControlCenter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        particle2Selection.setModel(new DefaultComboBoxModel(madeReactions.toArray()));
        setSelectedParticle2(this.particle2Selection.getSelectedItem().toString());

        //Reset functions
        submit.setText("Save");
        submit.setEnabled(false);
        newCollision.setEnabled(true);
        particle2Selection.setEnabled(true);
    }

    public void setSelectedParticle2(String selection) {
        String filePath = textPath + particle1Selection.getSelectedItem().toString() + ".txt";
        if (selection.startsWith("Default:")) {
            selection = "-" + selection.substring(8);
        }
        Scanner in = null;
        try {
            in = new Scanner(new File(filePath));
            while (in.hasNext()) {
                String line = in.nextLine();
                if (line.equals(selection)) {
                    String reaction = null;
                    String args = "";
                    if (line.startsWith("-")) {
                        int i = line.indexOf(" ");
                        if (i < 0) {
                            reaction = line.substring(1);
                        } else {
                            reaction = line.substring(1, i);
                            args = line.substring(i + 1);
                        }
                    } else {
                        line = in.nextLine();
                        int i = line.indexOf(" ");
                        if (i < 0) {
                            reaction = line;
                        } else {
                            reaction = line.substring(0, i);
                            args = line.substring(i + 1);
                        }
                    }
                    int index = getIndexOfReaction(reaction);
                    if (index < 0) {
                        refreshReactions();
                        index = getIndexOfReaction(reaction);
                        System.err.println("Could not find reaction");
                    }
                    reactionSelection.setSelectedIndex(index);
                    this.argumentTextBox.setText(args);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParticleControlCenter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public int getIndexOfReaction(String reaction) {
        for (int i = 0; i < reactionSelection.getItemCount(); i++) {
            if (reactionSelection.getItemAt(i).equals(reaction)) {
                return i;
            }
        }
        return -1;
    }

    public void refreshReactions() {
        reactionSelection.setModel(new DefaultComboBoxModel(getReactions().toArray()));
    }

    public void refreshParticles() {
        ComboBoxModel m = new DefaultComboBoxModel(getParticles().toArray());
        particle1Selection.setModel(m);
        setSelectedParticle(particle1Selection.getSelectedItem().toString());
    }

    private void particle1SelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_particle1SelectionActionPerformed
        //setup the seccond choices
        setSelectedParticle(particle1Selection.getSelectedItem().toString());
    }//GEN-LAST:event_particle1SelectionActionPerformed

    private void refreshParticlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshParticlesActionPerformed
        refreshParticles();
        checkForMissingDocuments();
    }//GEN-LAST:event_refreshParticlesActionPerformed

    private void refreshReactionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshReactionsActionPerformed
        refreshReactions();
        setSelectedParticle2(this.particle2Selection.getSelectedItem().toString());
    }//GEN-LAST:event_refreshReactionsActionPerformed

    private void argumentTextBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_argumentTextBoxKeyTyped
        submit.setEnabled(true);
    }//GEN-LAST:event_argumentTextBoxKeyTyped

    private void newCollisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCollisionActionPerformed
        ArrayList<String> list = getParticles();
        for (int i = 0; i < particle2Selection.getItemCount(); i++) {
            list.remove(particle2Selection.getItemAt(i));
        }

        String s = (String) JOptionPane.showInputDialog(
                frame,
                "Select A Particle",
                "New Custom Reaction",
                JOptionPane.PLAIN_MESSAGE,
                null,
                list.toArray(),
                "");

        //If a string was returned, say so.
        if ((s != null) && (s.length() > 0)) {
            submit.setEnabled(true);
            submit.setText("Add New Reaction Control");
            particle2Selection.addItem(s);
            particle2Selection.setSelectedItem(s);
            particle2Selection.setEnabled(false);
            newCollision.setEnabled(false);
            reactionSelection.requestFocus();
            argumentTextBox.setText("");
        }
    }//GEN-LAST:event_newCollisionActionPerformed

    private void reactionSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reactionSelectionActionPerformed
        submit.setEnabled(true);
    }//GEN-LAST:event_reactionSelectionActionPerformed

    private void particle2SelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_particle2SelectionActionPerformed
        setSelectedParticle2(particle2Selection.getSelectedItem().toString());
    }//GEN-LAST:event_particle2SelectionActionPerformed

    private void reactionSelectionPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_reactionSelectionPropertyChange
    }//GEN-LAST:event_reactionSelectionPropertyChange

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        String part1 = particle1Selection.getSelectedItem().toString();
        String part2 = particle2Selection.getSelectedItem().toString();
        String reaction = reactionSelection.getSelectedItem().toString();
        String args = argumentTextBox.getText();
        submitChange(part1, part2, reaction, args);
        if(bothFiles.isSelected() && !part2.startsWith("Default:") && !part1.equals(part2)){
            submitChange(part2, part1, reaction, args);
        }
        refreshParticles();
    }//GEN-LAST:event_submitActionPerformed

    public void submitChange(String part1, String part2, String reaction, String args) {
        File f = new File(textPath+part1+".txt");
        Scanner in = null;
        String newFile = "";
        boolean changed = false;
        
        if(args.length() > 0){
            args = " "+args;
        }
        
        try {
            in = new Scanner(f);
            while (in.hasNext()) {
                String line = in.nextLine();
                if (line.startsWith("-")) {
                    //If we are changing the default
                    if (part2.startsWith("Default:")) {
                        newFile += "-" + reaction+ args;
                    } else {
                        //If nothing is specified for part2
                        if (!changed) {
                            newFile += part2 + "\n" + reaction + args + "\n";
                        }
                        newFile += line;
                    }
                    break;
                } else {
                    newFile += line + "\n";
                }
                //If something else was specified change it
                if (line.equals(part2)) {
                    changed = true;
                    newFile += reaction + args + "\n";
                    in.nextLine();
                } else {
                    newFile += in.nextLine() + "\n";
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParticleControlCenter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                in.close();
            }
        }
                
        //Save the text we just made
        in = new Scanner(newFile);
        PrintWriter out = null;
        try {
            out = new PrintWriter(f);
            while (in.hasNext()) {
                out.println(in.nextLine());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParticleControlCenter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
            in.close();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea argumentTextBox;
    private javax.swing.JCheckBox bothFiles;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton newCollision;
    private javax.swing.JComboBox particle1Selection;
    private javax.swing.JComboBox particle2Selection;
    private javax.swing.JComboBox reactionSelection;
    private javax.swing.JButton refreshParticles;
    private javax.swing.JButton refreshReactions;
    private javax.swing.JButton submit;
    // End of variables declaration//GEN-END:variables
}
