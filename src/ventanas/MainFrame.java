/*
 * Clase perteneciente a la aplicación java Health Sherpa.
 * Para ejecutar la aplicación correr en el emulador la clase HealthSherpa.
 * @author Iván Taghavi Espinosa.
 * @version 1.0 2017
 */
package ventanas;

import static java.lang.Double.parseDouble;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import logica.Alimentos;
import logica.Calculos;
import logica.Registros;
import logica.Ingesta;

import logica.User;
import logica.Recetas;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import logica.Ingrediente;

/**
 *
 * @author Ivan
 */
public class MainFrame extends javax.swing.JFrame {
    
    private User usuario;
    
    private ArrayList<Ingesta> listaIngesta;
    private double caloriasObjetivo;
    private double protObjetivo;
    private double grasObjetivo;
    private double chObjetivo;
    private Date fechaActiva;
    private Registros ultimo;
    
    public static CustomListModelAlimentos list_modelAlimentos;
    public static CustomListModelRecetas list_modelRecetas;
    public static CustomListModelIngredientes list_modelIngredientes;
    
    
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        fechaActiva = new Date();
        this.calendarioPerfil.setDate(fechaActiva);
        int categoria = this.comboCategoria.getSelectedIndex();
        list_modelAlimentos = new CustomListModelAlimentos(categoria);
        this.jListAlimentosDiario.setModel(list_modelAlimentos);
        list_modelRecetas = new CustomListModelRecetas();
        this.listRecetas.setModel(list_modelRecetas);
        this.calendarioPerfil.setTodayButtonVisible(true);
        this.calendarioPerfil.setWeekOfYearVisible(false);
        
        //System.out.print(this.usuario.getGenero());
        
    }
    
    public MainFrame(User usuario){
        this();
        
        this.usuario=usuario;
        
        if(this.usuario.getTipoPlan()==0){
            this.radioPlanChMod.setSelected(true);
        } else
        {
            this.radioPlanCeto.setSelected(true);
        }
        
        this.cargaPanelPerfil();
        this.CargaPanelDiario();
        this.cargaPanelAjustes();
       
        
    }
    
    public void cargaPanelAjustes()
    {
        
        this.txtCalAjustes.setText(String.format("%.2f", Calculos.calcularCaloriasObjetivo(this.usuario, Calculos.calcularGET(usuario, Calculos.calcularTMB(usuario, ultimo.getPeso())))));
        
        if(this.radioPlanChMod.isSelected() && this.radioGramos.isSelected())
        {   
        this.txtProtAjustes.setText(String.format("%.2f", Calculos.calcularProtObjetivo(this.usuario, Calculos.calcularGET(usuario, Calculos.calcularTMB(usuario, ultimo.getPeso())))));
        this.txtCHajustes.setText(String.format("%.2f", Calculos.CalcularChObjetivo(this.usuario, Calculos.calcularGET(usuario, Calculos.calcularTMB(usuario, ultimo.getPeso())))));
        this.txtGrAjustes.setText(String.format("%.2f", Calculos.CalcularGrObjetivo(this.usuario, Calculos.calcularGET(usuario, Calculos.calcularTMB(usuario, ultimo.getPeso())))));
        this.lblProtAjustes.setText("gramos");
        this.lblGrAjustes.setText("gramos");
        this.lblChAjustes.setText("gramos");
        } else if (this.radioPlanCeto.isSelected() && this.radioGramos.isSelected())
        {
        this.txtProtAjustes.setText(String.format("%.2f", Calculos.calcularProtObjetivoCeto(this.usuario, Calculos.calcularGET(usuario, Calculos.calcularTMB(usuario, ultimo.getPeso())))));
        this.txtCHajustes.setText(String.format("%.2f", Calculos.CalcularChObjetivoCeto(this.usuario, Calculos.calcularGET(usuario, Calculos.calcularTMB(usuario, ultimo.getPeso())))));
        this.txtGrAjustes.setText(String.format("%.2f", Calculos.CalcularGrObjetivoCeto(this.usuario, Calculos.calcularGET(usuario, Calculos.calcularTMB(usuario, ultimo.getPeso())))));
        this.lblProtAjustes.setText("gramos");
        this.lblGrAjustes.setText("gramos");
        this.lblChAjustes.setText("gramos");
        } else if (this.radioPlanChMod.isSelected() && this.radioPorcentaje.isSelected())
        {
            this.txtProtAjustes.setText(""+Calculos.calcularProtObjetivoPorcentaje(usuario));
            this.txtCHajustes.setText(""+Calculos.calcularChObjetivoPorcentaje(usuario));
            this.txtGrAjustes.setText(""+Calculos.calcularGrObjetivoPorcentaje(usuario));
            this.lblProtAjustes.setText("%");
            this.lblGrAjustes.setText("%");
            this.lblChAjustes.setText("%");
        } else if (this.radioPlanCeto.isSelected() && this.radioPorcentaje.isSelected())
        {
            this.txtProtAjustes.setText(""+Calculos.calcularProtObjetivoPorcentaje(usuario));
            this.txtCHajustes.setText(""+Calculos.calcularChObjetivoPorcentaje(usuario));
            this.txtGrAjustes.setText(""+Calculos.calcularGrObjetivoPorcentaje(usuario));
            this.lblProtAjustes.setText("%");
            this.lblGrAjustes.setText("%");
            this.lblChAjustes.setText("%");
        }
        
        
    }

    public void cargaPanelPerfil(){
      
        if(this.usuario.getGenero().equals(User.HOMBRE)){
            this.txtIntroCadera.setEnabled(false);
        }
       
        this.ultimo=Registros.getUltimoRegistro(this.usuario.getIdUsuario());
        
        this.listaIngesta=Ingesta.getIngestasPorFecha(usuario.getIdUsuario(), this.fechaActiva );
        
         
        System.out.println(this.ultimo.getPeso());
        System.out.println(this.ultimo.getPeso());
        this.txtPesoHoy.setText(""+ this.ultimo.getPeso());
        this.txtFatPercent.setText(String.format("%.2f", Calculos.calcularPorcentajeGrasa(this.usuario, this.ultimo.getCintura(), ultimo.getCuello(), ultimo.getCadera())));
        this.txtCaloriasConsumidas.setText(""+Calculos.calculaSumaCalorias(listaIngesta));
        this.txtCaloriasObjetivo.setText(String.format("%.2f", Calculos.calcularCaloriasObjetivo(this.usuario, Calculos.calcularGET(usuario, Calculos.calcularTMB(usuario, ultimo.getPeso())))));
        
        if(usuario.getGenero().equals(User.HOMBRE)){
            this.jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthsherpa/images/fatHombre.png")));
        } else {
            this.jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthsherpa/images/fatMujer.png")));
        }
    }
    
    public void CargaPanelDiario(){
        ArrayList<Ingesta> listaDesayuno = Ingesta.getIngestasPorFechayTimming(this.usuario.getIdUsuario(), this.fechaActiva, 1);
        ArrayList<Ingesta> listaComida = Ingesta.getIngestasPorFechayTimming(this.usuario.getIdUsuario(), this.fechaActiva, 2);
        ArrayList<Ingesta> listaCena = Ingesta.getIngestasPorFechayTimming(this.usuario.getIdUsuario(), this.fechaActiva, 3);
        ArrayList<Ingesta> listaSnacks = Ingesta.getIngestasPorFechayTimming(this.usuario.getIdUsuario(), this.fechaActiva, 4);
        
        this.txtIntroPeso.setText("Peso");
        this.txtIntroCuello.setText("Cuello");
        this.txtIntroCadera.setText("Cadera");
        this.txtIntroCintura.setText("Cintura");
        
        this.listaIngesta = Ingesta.getIngestasPorFecha(usuario.getIdUsuario(), this.fechaActiva);
        
        if(this.usuario.getTipoPlan()==0)
        {
            this.caloriasObjetivo = Calculos.calcularCaloriasObjetivo(this.usuario, Calculos.calcularGET(usuario, Calculos.calcularTMB(usuario, ultimo.getPeso())));
            this.protObjetivo = Calculos.calcularProtObjetivo(this.usuario, this.caloriasObjetivo);
            this.grasObjetivo = Calculos.CalcularGrObjetivo(this.usuario, this.caloriasObjetivo);
            this.chObjetivo  = Calculos.CalcularChObjetivo(this.usuario, this.caloriasObjetivo);
            
        } else
        {
           this.caloriasObjetivo = Calculos.calcularCaloriasObjetivo(this.usuario, Calculos.calcularGET(usuario, Calculos.calcularTMB(usuario, ultimo.getPeso())));
           this.protObjetivo = Calculos.calcularProtObjetivoCeto(this.usuario, this.caloriasObjetivo);
           this.grasObjetivo = Calculos.CalcularGrObjetivoCeto(this.usuario, this.caloriasObjetivo);
           this.chObjetivo  = Calculos.CalcularChObjetivoCeto(this.usuario, this.caloriasObjetivo); 
          
        }
        
        this.labelCalRest.setText("Calorias restantes: " + String.format("%.2f",(this.caloriasObjetivo - Calculos.calculaSumaCalorias(listaIngesta))) + " Kcal");
        this.labelProtRes.setText("Proteina restante: " + String.format("%.2f",(this.protObjetivo - Calculos.calculaSumaProt(listaIngesta))) + " gr");
        this.labelGrasRes.setText("Grasa restante: " + String.format("%.2f",(this.grasObjetivo - Calculos.calculaSumaGr(listaIngesta))) + " gr");
        this.labelChRes.setText("CH restante: " + String.format("%.2f",(this.chObjetivo - Calculos.calculaSumaCh(listaIngesta))) + " gr");
        
        this.labelCalConsum.setText("Calorias consumidas: " + String.format("%.2f",Calculos.calculaSumaCalorias(listaIngesta)) + " Kcal");
        this.labelProtCons.setText("Proteina consumida: " + String.format("%.2f",Calculos.calculaSumaProt(listaIngesta)) + " gr");
        this.labelGrasCon.setText("Grasas consumidas: "+ String.format("%.2f",Calculos.calculaSumaGr(listaIngesta)) + " gr");
        this.labelChCon.setText("CH consumidos: "+ String.format("%.2f",Calculos.calculaSumaCh(listaIngesta)) + " gr");
        
        this.labelDesayunoResumen.setText("Calorias totales: "+String.format("%.2f", Calculos.calculaSumaCalorias(listaDesayuno))+" Kcal, Proteina: "+String.format("%.2f",Calculos.calculaSumaProt(listaDesayuno)) + " gr, Grasas: " + String.format("%.2f",Calculos.calculaSumaGr(listaDesayuno)) + "gr, CH: " + String.format("%.2f",Calculos.calculaSumaCh(listaDesayuno)) + " gr");
        this.labelComidaResumen.setText("Calorias totales: "+String.format("%.2f", Calculos.calculaSumaCalorias(listaComida))+" Kcal, Proteina: "+String.format("%.2f",Calculos.calculaSumaProt(listaComida)) + " gr, Grasas: " + String.format("%.2f",Calculos.calculaSumaGr(listaComida)) + "gr, CH: " + String.format("%.2f",Calculos.calculaSumaCh(listaComida)) + " gr");
        this.labelCenaResumen.setText("Calorias totales: "+String.format("%.2f", Calculos.calculaSumaCalorias(listaCena))+" Kcal, Proteina: "+String.format("%.2f",Calculos.calculaSumaProt(listaCena)) + " gr, Grasas: " + String.format("%.2f",Calculos.calculaSumaGr(listaCena)) + "gr, CH: " + String.format("%.2f",Calculos.calculaSumaCh(listaCena)) + " gr");
        this.labelSnacksResumen.setText("Calorias totales: "+String.format("%.2f", Calculos.calculaSumaCalorias(listaSnacks))+" Kcal, Proteina: "+String.format("%.2f",Calculos.calculaSumaProt(listaSnacks)) + " gr, Grasas: " + String.format("%.2f",Calculos.calculaSumaGr(listaSnacks)) + "gr, CH: " + String.format("%.2f",Calculos.calculaSumaCh(listaSnacks)) + " gr");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpDistribucionMacros = new javax.swing.ButtonGroup();
        grpMostrarMacros = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        PanelPerfil = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        calendarioPerfil = new com.toedter.calendar.JCalendar();
        jLabel2 = new javax.swing.JLabel();
        txtPesoHoy = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCaloriasConsumidas = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCaloriasObjetivo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtFatPercent = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        PanelDiario = new javax.swing.JPanel();
        comboTiming = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListAlimentosDiario = new javax.swing.JList<>();
        labelCalConsum = new javax.swing.JLabel();
        labelCalRest = new javax.swing.JLabel();
        labelProtCons = new javax.swing.JLabel();
        labelProtRes = new javax.swing.JLabel();
        labelGrasCon = new javax.swing.JLabel();
        labelGrasRes = new javax.swing.JLabel();
        labelChCon = new javax.swing.JLabel();
        labelChRes = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        labelDesayunoResumen = new javax.swing.JLabel();
        labelComidaResumen = new javax.swing.JLabel();
        labelCenaResumen = new javax.swing.JLabel();
        labelSnacksResumen = new javax.swing.JLabel();
        btnAnadirAlimento = new javax.swing.JButton();
        btnEliminarAlimento = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        txtIntroCuello = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        txtIntroCintura = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtIntroCadera = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        btnRegistraMedidas = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        txtIntroPeso = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnIngesta = new javax.swing.JButton();
        PanelRecetas = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listRecetas = new javax.swing.JList<>();
        btnNuevaReceta = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        listIngredientes = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        PanelAyuda = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        PanelAjustes = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        txtCalAjustes = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        radioPorcentaje = new javax.swing.JRadioButton();
        radioGramos = new javax.swing.JRadioButton();
        jLabel41 = new javax.swing.JLabel();
        txtProtAjustes = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtGrAjustes = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtCHajustes = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtNuevoUserName = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        nuevoPassword = new javax.swing.JPasswordField();
        btnNuevoUsername = new javax.swing.JButton();
        btnNuevaPassword = new javax.swing.JButton();
        btnNuevoUseryPass = new javax.swing.JButton();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        btnPerderPeso = new javax.swing.JButton();
        btnMantenerPeso = new javax.swing.JButton();
        btnGanarPeso = new javax.swing.JButton();
        radioPlanCeto = new javax.swing.JRadioButton();
        radioPlanChMod = new javax.swing.JRadioButton();
        comboNivelActividad = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblProtAjustes = new javax.swing.JLabel();
        lblGrAjustes = new javax.swing.JLabel();
        lblChAjustes = new javax.swing.JLabel();
        btnEliminarUser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Bienvenido ");

        calendarioPerfil.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calendarioPerfilPropertyChange(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Peso: ");

        txtPesoHoy.setEditable(false);
        txtPesoHoy.setBackground(new java.awt.Color(255, 255, 255));
        txtPesoHoy.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Kg");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Calorias consumidas hoy:");

        txtCaloriasConsumidas.setEditable(false);
        txtCaloriasConsumidas.setBackground(new java.awt.Color(255, 255, 255));
        txtCaloriasConsumidas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Kcal");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Calorias objetivo:");

        txtCaloriasObjetivo.setEditable(false);
        txtCaloriasObjetivo.setBackground(new java.awt.Color(255, 255, 255));
        txtCaloriasObjetivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Kcal");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Porcentaje de grasa corporal:");

        txtFatPercent.setEditable(false);
        txtFatPercent.setBackground(new java.awt.Color(255, 255, 255));
        txtFatPercent.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("%");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Selecciona el día para el que quieres comprobar tus registros");

        javax.swing.GroupLayout PanelPerfilLayout = new javax.swing.GroupLayout(PanelPerfil);
        PanelPerfil.setLayout(PanelPerfilLayout);
        PanelPerfilLayout.setHorizontalGroup(
            PanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPerfilLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPerfilLayout.createSequentialGroup()
                        .addGroup(PanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelPerfilLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(37, 37, 37)
                                .addComponent(txtCaloriasConsumidas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelPerfilLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCaloriasObjetivo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelPerfilLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFatPercent, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(PanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)))
                    .addComponent(jLabel1)
                    .addComponent(jLabel14)
                    .addGroup(PanelPerfilLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(136, 136, 136)
                        .addComponent(txtPesoHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 255, Short.MAX_VALUE)
                .addComponent(calendarioPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116))
            .addGroup(PanelPerfilLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelPerfilLayout.setVerticalGroup(
            PanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPerfilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPerfilLayout.createSequentialGroup()
                        .addComponent(calendarioPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel3))
                    .addGroup(PanelPerfilLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addGroup(PanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtPesoHoy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addComponent(jLabel2))
                        .addGap(21, 21, 21)
                        .addGroup(PanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtCaloriasConsumidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(PanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtCaloriasObjetivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(PanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFatPercent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))))
                .addContainerGap(354, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Perfil", PanelPerfil);

        comboTiming.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Desayuno", "Comida", "Cena", "Snacks" }));
        comboTiming.setActionCommand("1,\n2,\n3,\n4,");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Escoge comida:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setText("Registro de alimentos");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Grupo de alimentos:");

        comboCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Leche y derivados", "Carne pescados y huevos", "Legumbres y frutos secos", "Hortalizas y verduras", "Frutas", "Pasta, pan, cereales y azucar", "Grasas y aceites", "Otros", "Recetas" }));
        comboCategoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboCategoriaItemStateChanged(evt);
            }
        });

        jListAlimentosDiario.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListAlimentosDiario.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListAlimentosDiarioValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListAlimentosDiario);

        labelCalConsum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelCalConsum.setText("Calorías consumidas:");

        labelCalRest.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelCalRest.setText("Calorías restantes: ");

        labelProtCons.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelProtCons.setText("Proteinas consumidas:");

        labelProtRes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelProtRes.setText("Proteinas restantes:");

        labelGrasCon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelGrasCon.setText("Grasas consumidas:");

        labelGrasRes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelGrasRes.setText("Grasas restantes:");

        labelChCon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelChCon.setText("Carbohidratos consumidos:");

        labelChRes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelChRes.setText("Carbohidratos restantes:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("Desayuno:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setText("Comida:");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setText("Cena:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setText("Snacks:");

        labelDesayunoResumen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelDesayunoResumen.setText("Calorias totales:  CH:  Grasas:  Proteina:");

        labelComidaResumen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelComidaResumen.setText("Calorias totales:  CH:  Grasas:  Proteina:");

        labelCenaResumen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelCenaResumen.setText("Calorias totales:  CH:  Grasas:  Proteina:");

        labelSnacksResumen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelSnacksResumen.setText("Calorias totales:  CH:  Grasas:  Proteina:");

        btnAnadirAlimento.setText("Añadir alimento");
        btnAnadirAlimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirAlimentoActionPerformed(evt);
            }
        });

        btnEliminarAlimento.setText("Eliminar alimento");
        btnEliminarAlimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarAlimentoActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel47.setText("Registro personal");

        txtIntroCuello.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIntroCuello.setText("Cuello");

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel48.setText("Cm");

        txtIntroCintura.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIntroCintura.setText("Cintura");

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel49.setText("Cm");

        txtIntroCadera.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIntroCadera.setText("Cadera");

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel50.setText("Cm");

        btnRegistraMedidas.setText("Registrar medidas");
        btnRegistraMedidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraMedidasActionPerformed(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel54.setText("Cantidad:");

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel55.setText("Gr");

        txtIntroPeso.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIntroPeso.setText("Peso");

        jLabel57.setText("Kg");

        jLabel6.setText("*Los valores de los alimentos son por cada 100gr");

        btnIngesta.setText("Registra ingesta");
        btnIngesta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngestaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelDiarioLayout = new javax.swing.GroupLayout(PanelDiario);
        PanelDiario.setLayout(PanelDiarioLayout);
        PanelDiarioLayout.setHorizontalGroup(
            PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDiarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDiarioLayout.createSequentialGroup()
                        .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addGroup(PanelDiarioLayout.createSequentialGroup()
                                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboTiming, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel6)
                            .addGroup(PanelDiarioLayout.createSequentialGroup()
                                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelDiarioLayout.createSequentialGroup()
                                        .addComponent(jLabel54)
                                        .addGap(67, 67, 67)
                                        .addComponent(jLabel55))
                                    .addGroup(PanelDiarioLayout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnAnadirAlimento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnEliminarAlimento)
                                    .addComponent(btnIngesta))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addGroup(PanelDiarioLayout.createSequentialGroup()
                                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelDiarioLayout.createSequentialGroup()
                                        .addComponent(txtIntroPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel57)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtIntroCuello, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel48)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtIntroCintura, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(labelGrasRes)
                                    .addComponent(labelGrasCon)
                                    .addComponent(labelChRes)
                                    .addComponent(labelChCon)
                                    .addComponent(labelCalConsum)
                                    .addComponent(labelCalRest)
                                    .addComponent(labelProtCons)
                                    .addComponent(labelProtRes))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel31)
                                    .addComponent(labelDesayunoResumen)
                                    .addComponent(labelCenaResumen)
                                    .addComponent(labelComidaResumen)
                                    .addComponent(labelSnacksResumen)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel29)
                                    .addGroup(PanelDiarioLayout.createSequentialGroup()
                                        .addComponent(jLabel49)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtIntroCadera, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel50)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnRegistraMedidas))))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        PanelDiarioLayout.setVerticalGroup(
            PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDiarioLayout.createSequentialGroup()
                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDiarioLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(labelCalConsum))
                        .addGap(18, 18, 18)
                        .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(comboTiming, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCalRest))
                        .addGap(18, 18, 18)
                        .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelDiarioLayout.createSequentialGroup()
                                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(19, 19, 19)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
                            .addGroup(PanelDiarioLayout.createSequentialGroup()
                                .addComponent(labelProtCons)
                                .addGap(18, 18, 18)
                                .addComponent(labelProtRes)
                                .addGap(18, 18, 18)
                                .addComponent(labelGrasCon)
                                .addGap(18, 18, 18)
                                .addComponent(labelGrasRes)
                                .addGap(18, 18, 18)
                                .addComponent(labelChCon)
                                .addGap(18, 18, 18)
                                .addComponent(labelChRes)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDiarioLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDiarioLayout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIntroCuello, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel48)
                                    .addComponent(txtIntroCintura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel49)
                                    .addComponent(txtIntroCadera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel50)
                                    .addComponent(txtIntroPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel57)
                                    .addComponent(btnRegistraMedidas)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDiarioLayout.createSequentialGroup()
                                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelDiarioLayout.createSequentialGroup()
                                        .addGap(73, 73, 73)
                                        .addComponent(jLabel30)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelComidaResumen)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel31)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelCenaResumen)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel32)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelSnacksResumen))
                                    .addGroup(PanelDiarioLayout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelDesayunoResumen)))
                                .addGap(200, 200, 200)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55)
                    .addComponent(btnIngesta))
                .addGap(21, 21, 21)
                .addGroup(PanelDiarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminarAlimento)
                    .addComponent(btnAnadirAlimento))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Diario", PanelDiario);

        jLabel37.setText("Lista de recetas:");

        listRecetas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listRecetasValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listRecetas);

        btnNuevaReceta.setText("Nueva receta");
        btnNuevaReceta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaRecetaActionPerformed(evt);
            }
        });

        txtDescripcion.setEditable(false);
        txtDescripcion.setColumns(20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        jScrollPane3.setViewportView(txtDescripcion);

        jScrollPane5.setViewportView(listIngredientes);

        jLabel7.setText("Ingredientes:");

        btnEliminar.setText("Eliminar receta");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelRecetasLayout = new javax.swing.GroupLayout(PanelRecetas);
        PanelRecetas.setLayout(PanelRecetasLayout);
        PanelRecetasLayout.setHorizontalGroup(
            PanelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRecetasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(18, 18, 18)
                .addGroup(PanelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                    .addComponent(jScrollPane5)
                    .addGroup(PanelRecetasLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PanelRecetasLayout.createSequentialGroup()
                        .addComponent(btnNuevaReceta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar)))
                .addContainerGap())
        );
        PanelRecetasLayout.setVerticalGroup(
            PanelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelRecetasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelRecetasLayout.createSequentialGroup()
                        .addComponent(jScrollPane5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelRecetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevaReceta)
                            .addComponent(btnEliminar))
                        .addGap(7, 7, 7))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Recetas", PanelRecetas);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setText("Bienvenido a Health Sherpa\n\nEn este breve manual de ayuda le daremos unas pinceladas sobre el uso de la \naplicación y recomendaciones personales.\n\nEl objetivo de esta aplicación es ayudarte a perder o ganar peso pero en\nfunción de tu porcentaje de grasa corporal, para maximizar el impacto\nsobre la salud y sobre tu estética. \n\nA traves del registro y seguimiento de tus números podras ajustar el balance\nenergético para lograr tus objetivos, pero a pesar de que el metabolismo\nse explica en base a las leyes de la termodinámica, desde esta aplicación\nconsideramos que no es lo mismo isocalórico que isometabólico, y por eso\nqueremos hacer incapié en la importancia de que la base de tu alimentación\nsea comida real y no productos procesados o ultraprocesados, pues estos\núltimos van a tener un impacto negativo en tus hormonas y en tu estado de\nsalud. ");
        jTextArea2.setEnabled(false);
        jScrollPane4.setViewportView(jTextArea2);

        javax.swing.GroupLayout PanelAyudaLayout = new javax.swing.GroupLayout(PanelAyuda);
        PanelAyuda.setLayout(PanelAyudaLayout);
        PanelAyudaLayout.setHorizontalGroup(
            PanelAyudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
        );
        PanelAyudaLayout.setVerticalGroup(
            PanelAyudaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Ayuda", PanelAyuda);

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel38.setText("Ajustar calorías manualmente:");

        txtCalAjustes.setEditable(false);
        txtCalAjustes.setBackground(new java.awt.Color(255, 255, 255));
        txtCalAjustes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel39.setText("Kcal");

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel40.setText("Mostrar macronutrientes:");

        grpMostrarMacros.add(radioPorcentaje);
        radioPorcentaje.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioPorcentaje.setText("Porcentaje");
        radioPorcentaje.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioPorcentajeItemStateChanged(evt);
            }
        });

        grpMostrarMacros.add(radioGramos);
        radioGramos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioGramos.setSelected(true);
        radioGramos.setText("Gramos");
        radioGramos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioGramosItemStateChanged(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel41.setText("Proteinas:");

        txtProtAjustes.setEditable(false);
        txtProtAjustes.setBackground(new java.awt.Color(255, 255, 255));
        txtProtAjustes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel42.setText("Grasas:");

        txtGrAjustes.setEditable(false);
        txtGrAjustes.setBackground(new java.awt.Color(255, 255, 255));
        txtGrAjustes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel43.setText("CH:");

        txtCHajustes.setEditable(false);
        txtCHajustes.setBackground(new java.awt.Color(255, 255, 255));
        txtCHajustes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel44.setText("Usuario:");

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel45.setText("Nombre usuario:");

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setText("Contraseña:");

        btnNuevoUsername.setText("Cambiar nombre usuario");
        btnNuevoUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoUsernameActionPerformed(evt);
            }
        });

        btnNuevaPassword.setText("Cambiar contraseña");
        btnNuevaPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaPasswordActionPerformed(evt);
            }
        });

        btnNuevoUseryPass.setText("Cambiar nombre y contraseña");
        btnNuevoUseryPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoUseryPassActionPerformed(evt);
            }
        });

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setText("Ajustes de cuenta");

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel52.setText("Ajustes de aplicación");

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel53.setText("Escoger tipo de dieta:");

        btnPerderPeso.setText("Perder peso");
        btnPerderPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerderPesoActionPerformed(evt);
            }
        });

        btnMantenerPeso.setText("Mantener peso");
        btnMantenerPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMantenerPesoActionPerformed(evt);
            }
        });

        btnGanarPeso.setText("Ganar peso");
        btnGanarPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGanarPesoActionPerformed(evt);
            }
        });

        grpDistribucionMacros.add(radioPlanCeto);
        radioPlanCeto.setText("Cetogénica");
        radioPlanCeto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioPlanCetoItemStateChanged(evt);
            }
        });

        grpDistribucionMacros.add(radioPlanChMod);
        radioPlanChMod.setSelected(true);
        radioPlanChMod.setText("Moderada en CH");
        radioPlanChMod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radioPlanChModItemStateChanged(evt);
            }
        });

        comboNivelActividad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sedentario", "Algo activo", "Activo", "Muy activo", "Deportista profesional" }));
        comboNivelActividad.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboNivelActividadItemStateChanged(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel56.setText("Nivel de actividad:");

        btnLogin.setText("Volver al loguin");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel4.setText("*Funcionalidad no disponible sin la versión premium");

        lblProtAjustes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProtAjustes.setText("gramos");

        lblGrAjustes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblGrAjustes.setText("gramos");

        lblChAjustes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblChAjustes.setText("gramos");

        btnEliminarUser.setText("Eliminar usuario");
        btnEliminarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelAjustesLayout = new javax.swing.GroupLayout(PanelAjustes);
        PanelAjustes.setLayout(PanelAjustesLayout);
        PanelAjustesLayout.setHorizontalGroup(
            PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAjustesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAjustesLayout.createSequentialGroup()
                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addGroup(PanelAjustesLayout.createSequentialGroup()
                                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel45)
                                    .addComponent(jLabel46))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNuevoUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(nuevoPassword))
                                .addGap(18, 18, 18)
                                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnNuevoUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnNuevaPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnNuevoUseryPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel51))
                        .addGap(320, 320, 320))
                    .addGroup(PanelAjustesLayout.createSequentialGroup()
                        .addComponent(btnLogin)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PanelAjustesLayout.createSequentialGroup()
                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PanelAjustesLayout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCalAjustes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel39))
                            .addGroup(PanelAjustesLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radioPorcentaje)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radioGramos))
                            .addComponent(jLabel52)
                            .addComponent(jLabel4)
                            .addGroup(PanelAjustesLayout.createSequentialGroup()
                                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelAjustesLayout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel41)
                                            .addComponent(jLabel42)))
                                    .addComponent(jLabel43))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtGrAjustes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                                    .addComponent(txtProtAjustes, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCHajustes))
                                .addGap(6, 6, 6)
                                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblChAjustes)
                                    .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblProtAjustes, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblGrAjustes)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel53)
                            .addGroup(PanelAjustesLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PanelAjustesLayout.createSequentialGroup()
                                        .addComponent(radioPlanCeto)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radioPlanChMod))
                                    .addGroup(PanelAjustesLayout.createSequentialGroup()
                                        .addComponent(jLabel56)
                                        .addGap(18, 18, 18)
                                        .addComponent(comboNivelActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnPerderPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(btnGanarPeso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnMantenerPeso, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addComponent(btnEliminarUser)))))
                        .addGap(220, 220, 220))))
        );
        PanelAjustesLayout.setVerticalGroup(
            PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAjustesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51)
                .addGap(13, 13, 13)
                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNuevoUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(btnNuevoUsername))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(nuevoPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevaPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNuevoUseryPass)
                .addGap(27, 27, 27)
                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAjustesLayout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addGap(18, 18, 18)
                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(txtCalAjustes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(8, 8, 8)
                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioGramos)
                            .addComponent(radioPorcentaje)
                            .addComponent(jLabel40))
                        .addGap(5, 5, 5)
                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtProtAjustes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblProtAjustes)))
                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelAjustesLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel42))
                            .addGroup(PanelAjustesLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtGrAjustes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblGrAjustes))))
                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelAjustesLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel43))
                            .addGroup(PanelAjustesLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCHajustes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblChAjustes)))))
                    .addGroup(PanelAjustesLayout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addGap(7, 7, 7)
                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioPlanCeto)
                            .addComponent(radioPlanChMod))
                        .addGap(18, 18, 18)
                        .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(comboNivelActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnPerderPeso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMantenerPeso)
                        .addGap(14, 14, 14)
                        .addComponent(btnGanarPeso)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                .addGroup(PanelAjustesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnEliminarUser))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Ajustes", PanelAjustes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        this.dispose();
              java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginFrame login = new LoginFrame();
                login.setVisible(true);
            }
        });
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnAnadirAlimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirAlimentoActionPerformed
        // TODO add your handling code here:
        NuevoAlimentoFrame nuevoAlimentoFrame = new NuevoAlimentoFrame();
        nuevoAlimentoFrame.setVisible(true);
        
    }//GEN-LAST:event_btnAnadirAlimentoActionPerformed

    private void btnEliminarAlimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarAlimentoActionPerformed
        // TODO add your handling code here:
        
        int seleccion = this.jListAlimentosDiario.getSelectedIndex();
        
        if (seleccion!=-1) { //si no hay nada seleccionado devuelve -1
            Alimentos alimento = MainFrame.list_modelAlimentos.getAlimento(seleccion);
            MainFrame.list_modelAlimentos.eliminarAlimento(seleccion);
            alimento.borrar();
            }
    }//GEN-LAST:event_btnEliminarAlimentoActionPerformed

    private void jListAlimentosDiarioValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListAlimentosDiarioValueChanged
       
        
    }//GEN-LAST:event_jListAlimentosDiarioValueChanged

    private void comboCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboCategoriaItemStateChanged
        // TODO add your handling code here:
        int categoria = this.comboCategoria.getSelectedIndex();
        if(categoria!=8){
        list_modelAlimentos = new CustomListModelAlimentos(categoria);
        this.jListAlimentosDiario.setModel(list_modelAlimentos);
        this.btnEliminarAlimento.setEnabled(true);
        
        }else{
        this.jListAlimentosDiario.setModel(list_modelRecetas);
        this.btnEliminarAlimento.setEnabled(false);
        
        }
        
    }//GEN-LAST:event_comboCategoriaItemStateChanged

    private void listRecetasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listRecetasValueChanged
        // TODO add your handling code here:
        int selection = this.listRecetas.getSelectedIndex();
            if (selection!=-1) {
                Recetas receta = MainFrame.list_modelRecetas.getReceta(selection);
                list_modelIngredientes = new CustomListModelIngredientes(receta);
                this.listIngredientes.setModel(list_modelIngredientes);
                this.txtDescripcion.setText(receta.getDescripcion());
            }
        
    }//GEN-LAST:event_listRecetasValueChanged

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
       
        int seleccion = this.listRecetas.getSelectedIndex();
        
        if (seleccion!=-1) { //si no hay nada seleccionado devuelve -1
            Recetas receta = MainFrame.list_modelRecetas.getReceta(seleccion);
            MainFrame.list_modelRecetas.eliminarReceta(seleccion);
            receta.borrar();
            }
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevaRecetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaRecetaActionPerformed
        // TODO add your handling code here:
        NuevaRecetaFrame nueva = new NuevaRecetaFrame();
        nueva.setVisible(true);
        
    }//GEN-LAST:event_btnNuevaRecetaActionPerformed

    private void btnIngestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngestaActionPerformed
        // TODO add your handling code here:
        int categoria = this.comboCategoria.getSelectedIndex();
        int seleccion = this.jListAlimentosDiario.getSelectedIndex();
        int timing = this.comboTiming.getSelectedIndex() + 1;
        double cantidad = 0;
       
                if(categoria!=8)
                 {
                    Alimentos escogido = this.list_modelAlimentos.getAlimento(seleccion);
                    try{
                    cantidad = Double.parseDouble(this.txtCantidad.getText());
                    } catch (InputMismatchException e){
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Alerta", JOptionPane.ERROR_MESSAGE);
                        cantidad = 0;
                    } catch (NumberFormatException e)
                    {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Alerta", JOptionPane.ERROR_MESSAGE);
                        cantidad = 0;
                    }catch (Exception e){
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Alerta", JOptionPane.ERROR_MESSAGE);
                        cantidad = 0;
                    } 
                    Ingesta nueva = new Ingesta(this.usuario.getIdUsuario(), timing, escogido.getId_alimento(), cantidad, this.fechaActiva);
                    nueva.grabar();

                } else
                {

                    Recetas escogida = this.list_modelRecetas.getReceta(seleccion);
                    try{
                    cantidad = Double.parseDouble(this.txtCantidad.getText());
                     } catch (InputMismatchException e){
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Alerta", JOptionPane.ERROR_MESSAGE);
                        cantidad = 0;
                    } catch (NumberFormatException e)
                    {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Alerta", JOptionPane.ERROR_MESSAGE);
                        cantidad = 0;
                    } catch (Exception e){
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Alerta", JOptionPane.ERROR_MESSAGE);
                        cantidad = 0;
                    }
                    ArrayList<Ingrediente> ingredientes = escogida.getIngredientes();
                    Iterator<Ingrediente> itrIng = ingredientes.iterator();
                    while(itrIng.hasNext())
                    {
                        Ingrediente ingr = itrIng.next();
                        Ingesta nueva = new Ingesta(this.usuario.getIdUsuario(), timing, ingr.getId_alimento(),  ((ingr.getCantidad()*cantidad)/100), this.fechaActiva);
                        nueva.grabar();
                    }

                }
        
        
        this.CargaPanelDiario();
        this.cargaPanelPerfil();
        
        
    }//GEN-LAST:event_btnIngestaActionPerformed

    private void btnRegistraMedidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraMedidasActionPerformed
        // TODO add your handling code here:
       
            if(usuario.getGenero().equals(User.MUJER)){
                Registros nuevo = new Registros(this.usuario.getIdUsuario(), Double.parseDouble(this.txtIntroPeso.getText()), Double.parseDouble(this.txtIntroCintura.getText()), Double.parseDouble(this.txtIntroCuello.getText()), Double.parseDouble(this.txtIntroCadera.getText()), this.fechaActiva);
                nuevo.grabar();
            } else {
                Registros nuevo = new Registros(this.usuario.getIdUsuario(), Double.parseDouble(this.txtIntroPeso.getText()), Double.parseDouble(this.txtIntroCintura.getText()), Double.parseDouble(this.txtIntroCuello.getText()), 0, this.fechaActiva);
                nuevo.grabar();
            }
       
            this.cargaPanelPerfil();
            this.CargaPanelDiario();
        
    }//GEN-LAST:event_btnRegistraMedidasActionPerformed

    private void calendarioPerfilPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calendarioPerfilPropertyChange
        // TODO add your handling code here:
    
    }//GEN-LAST:event_calendarioPerfilPropertyChange

    private void radioPlanCetoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioPlanCetoItemStateChanged
        // TODO add your handling code here:
        if(this.radioPlanCeto.isSelected())
        {
            this.usuario.setTipoPlan(1);
            this.usuario.actualizarTipoPlan();
            this.CargaPanelDiario();
            this.cargaPanelAjustes();
        } else
        {
            this.usuario.setTipoPlan(0);
            this.usuario.actualizarTipoPlan();
            this.CargaPanelDiario();
            this.cargaPanelAjustes();
        }
    }//GEN-LAST:event_radioPlanCetoItemStateChanged

    private void radioPlanChModItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioPlanChModItemStateChanged
        // TODO add your handling code here:
        if(this.radioPlanChMod.isSelected())
        {
            this.usuario.setTipoPlan(0);
            this.usuario.actualizarTipoPlan();
            this.CargaPanelDiario();
            this.cargaPanelAjustes();
        } else
        {
            this.usuario.setTipoPlan(1);
            this.usuario.actualizarTipoPlan();
            this.CargaPanelDiario();
            this.cargaPanelAjustes();
        }
    }//GEN-LAST:event_radioPlanChModItemStateChanged

    private void btnPerderPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerderPesoActionPerformed
        // TODO add your handling code here:
        this.usuario.setObjetivo(1);
        this.usuario.actualizarObjetivo();
        this.cargaPanelPerfil();
        this.CargaPanelDiario();
        this.cargaPanelAjustes();
        
    }//GEN-LAST:event_btnPerderPesoActionPerformed

    private void btnMantenerPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMantenerPesoActionPerformed
        // TODO add your handling code here:
       this.usuario.setObjetivo(2);
        this.usuario.actualizarObjetivo();
        this.CargaPanelDiario();
        this.cargaPanelPerfil(); 
        this.cargaPanelAjustes();
        
    }//GEN-LAST:event_btnMantenerPesoActionPerformed

    private void btnGanarPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGanarPesoActionPerformed
        // TODO add your handling code here:
        this.usuario.setObjetivo(3);
        this.usuario.actualizarObjetivo();
        this.CargaPanelDiario();
        this.cargaPanelPerfil();
        this.cargaPanelAjustes();
        
    }//GEN-LAST:event_btnGanarPesoActionPerformed

    private void comboNivelActividadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboNivelActividadItemStateChanged
        // TODO add your handling code here:
        int nivelActividad = this.comboNivelActividad.getSelectedIndex();
        this.usuario.setNivel_actividad(nivelActividad);
        this.usuario.actualizarNivelActividad();
        this.CargaPanelDiario();
        this.cargaPanelPerfil();
        this.cargaPanelAjustes();
    }//GEN-LAST:event_comboNivelActividadItemStateChanged

    private void radioPorcentajeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioPorcentajeItemStateChanged
        // TODO add your handling code here:
        this.cargaPanelAjustes();
    }//GEN-LAST:event_radioPorcentajeItemStateChanged

    private void radioGramosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radioGramosItemStateChanged
        // TODO add your handling code here:
        this.cargaPanelAjustes();
    }//GEN-LAST:event_radioGramosItemStateChanged

    private void btnNuevoUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoUsernameActionPerformed
        // TODO add your handling code here:
        String nuevo = this.txtNuevoUserName.getText();
        this.usuario.setUsuario(nuevo);
        this.usuario.actualizarNombreUsuario();
        this.txtNuevoUserName.setText("");
    }//GEN-LAST:event_btnNuevoUsernameActionPerformed

    private void btnNuevaPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaPasswordActionPerformed
        // TODO add your handling code here:
        String pass = String.valueOf(this.nuevoPassword.getPassword());
        this.usuario.setPasswd(pass);
        this.usuario.actualizarPassword();
        this.nuevoPassword.setText("");
    }//GEN-LAST:event_btnNuevaPasswordActionPerformed

    private void btnNuevoUseryPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoUseryPassActionPerformed
        // TODO add your handling code here:
        String username = this.txtNuevoUserName.getText();
        this.usuario.setUsuario(username);
        String pass = String.valueOf(this.nuevoPassword.getPassword());
        this.usuario.setPasswd(pass);
        this.usuario.actualizarNombreyPass();
    }//GEN-LAST:event_btnNuevoUseryPassActionPerformed

    private void btnEliminarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUserActionPerformed
        // TODO add your handling code here:
        
        int decision = JOptionPane.showConfirmDialog(null, "Seguro que desea borrar al usuario? Se perderán todos los registros.", "Confirmar eliminar usuario", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(decision == JOptionPane.YES_OPTION){
            this.usuario.borrar();
            this.dispose();
              java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginFrame login = new LoginFrame();
                login.setVisible(true);
            }
        });
        }
        
    }//GEN-LAST:event_btnEliminarUserActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelAjustes;
    private javax.swing.JPanel PanelAyuda;
    private javax.swing.JPanel PanelDiario;
    private javax.swing.JPanel PanelPerfil;
    private javax.swing.JPanel PanelRecetas;
    private javax.swing.JButton btnAnadirAlimento;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarAlimento;
    private javax.swing.JButton btnEliminarUser;
    private javax.swing.JButton btnGanarPeso;
    private javax.swing.JButton btnIngesta;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnMantenerPeso;
    private javax.swing.JButton btnNuevaPassword;
    private javax.swing.JButton btnNuevaReceta;
    private javax.swing.JButton btnNuevoUsername;
    private javax.swing.JButton btnNuevoUseryPass;
    private javax.swing.JButton btnPerderPeso;
    private javax.swing.JButton btnRegistraMedidas;
    private com.toedter.calendar.JCalendar calendarioPerfil;
    private javax.swing.JComboBox<String> comboCategoria;
    private javax.swing.JComboBox<String> comboNivelActividad;
    private javax.swing.JComboBox<String> comboTiming;
    private javax.swing.ButtonGroup grpDistribucionMacros;
    private javax.swing.ButtonGroup grpMostrarMacros;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public static javax.swing.JList<String> jListAlimentosDiario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JLabel labelCalConsum;
    private javax.swing.JLabel labelCalRest;
    private javax.swing.JLabel labelCenaResumen;
    private javax.swing.JLabel labelChCon;
    private javax.swing.JLabel labelChRes;
    private javax.swing.JLabel labelComidaResumen;
    private javax.swing.JLabel labelDesayunoResumen;
    private javax.swing.JLabel labelGrasCon;
    private javax.swing.JLabel labelGrasRes;
    private javax.swing.JLabel labelProtCons;
    private javax.swing.JLabel labelProtRes;
    private javax.swing.JLabel labelSnacksResumen;
    private javax.swing.JLabel lblChAjustes;
    private javax.swing.JLabel lblGrAjustes;
    private javax.swing.JLabel lblProtAjustes;
    private javax.swing.JList<String> listIngredientes;
    private javax.swing.JList<String> listRecetas;
    private javax.swing.JPasswordField nuevoPassword;
    private javax.swing.JRadioButton radioGramos;
    private javax.swing.JRadioButton radioPlanCeto;
    private javax.swing.JRadioButton radioPlanChMod;
    private javax.swing.JRadioButton radioPorcentaje;
    private javax.swing.JTextField txtCHajustes;
    private javax.swing.JTextField txtCalAjustes;
    private javax.swing.JTextField txtCaloriasConsumidas;
    private javax.swing.JTextField txtCaloriasObjetivo;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtFatPercent;
    private javax.swing.JTextField txtGrAjustes;
    private javax.swing.JTextField txtIntroCadera;
    private javax.swing.JTextField txtIntroCintura;
    private javax.swing.JTextField txtIntroCuello;
    private javax.swing.JTextField txtIntroPeso;
    private javax.swing.JTextField txtNuevoUserName;
    private javax.swing.JTextField txtPesoHoy;
    private javax.swing.JTextField txtProtAjustes;
    // End of variables declaration//GEN-END:variables

    


}
