BEGIN TRANSACTION;
-- MODULO ---------------------------------> PEDIDOS
-- AGENDA
CREATE TABLE PROFFLINE_TB_AGENDA
(txtVendor_Id TEXT, 
txtVendor_Name TEXT, 
txtBegda TEXT, 
txtEndda TEXT, 
txtHora TEXT, 
txtCust_Id TEXT, 
txtCust_Name TEXT, 
txtCust_Addres TEXT, 
txtCust_Telf TEXT, 
txtCust_Telfx TEXT, 
txtCust_1 TEXT, 
txtCu_1 TEXT, 
txtCu_2 TEXT, 
txtCust_Klimk TEXT, 
txtCus TEXT,
txtCust_Available TEXT,
txtDescription TEXT,
txtSt TEXT,  
txtStat TEXT,    
txtTy TEXT,     
txtCust_2 TEXT,          
txtType_Description TEXT,
txtStdRegAge TEXT);
-- DETALE DE PEDIDO
CREATE TABLE PROFFLINE_TB_PEDIDO_DETALLE (tipo NUMERIC, id_header NUMERIC, id INTEGER PRIMARY KEY, txtSincronizado NUMERIC, txtPosicion TEXT, txtMaterial TEXT, txtCantidad TEXT, txtCantidadConfirmada TEXT, txtUM TEXT, txtPorcentajeDescuento TEXT, txtDenominacion TEXT, txtPrecioNeto TEXT, txtValorNeto TEXT);
-- CABECERA DE PEDIDO
CREATE TABLE PROFFLINE_TB_PEDIDO_HEADER (txtDisponible TEXT, txtLimiteCredito TEXT, txtClaseRiesgo TEXT, NOMBRE_CLIENTE TEXT, COD_CLIENTE TEXT, VALOR_NETO TEXT, txtVendedor TEXT, txtSincronizado NUMERIC, SHIP_TYPE TEXT, SHIP_COND TEXT, CREATED_BY TEXT, CURRENCY TEXT, SERV_DATE TEXT, BILL_DATE TEXT, DOC_DATE TEXT, SD_DOC_CAT TEXT, PURCH_NO_C TEXT, PRICE_DATE TEXT, DLV_BLOCK TEXT, PMNTTRMS TEXT, PURCH_DATE TEXT, REQ_DATE_H TEXT, SALES_OFF TEXT, SALES_GRP TEXT, DIVISION TEXT, DISTR_CHAN TEXT, SALES_ORG TEXT, DOC_TYPE TEXT, id INTEGER PRIMARY KEY, source integer, CodigoPedidoEnSap TEXT);
-- PARTNERS DE PEDIDO
CREATE TABLE PROFFLINE_TB_PEDIDO_PARTNERS (txtValue TEXT, txtKey TEXT, id INTEGER PRIMARY KEY, txtSincronizado NUMERIC, id_header NUMERIC);
-- MODULO ---------------------------------> COBRANZAS
CREATE TABLE PROFFLINE_TB_ANTICIPO_CLIENTE
(id INTEGER PRIMARY KEY,
codigoCliente TEXT,
codigoVendedor TEXT,
nombreCompletoCliente TEXT,
idFormaPago TEXT,
referencia TEXT,
importe TEXT,
nroCtaBcoCliente TEXT,
idBancoCliente TEXT,
idBancoPromesa TEXT,
observaciones TEXT,
fechaCreacion TEXT,
sincronizado TEXT,
anticipoSecuencia TEXT);

CREATE TABLE PROFFLINE_TB_FORMA_PAGO_ANTICIPO
(idFormaPago TEXT,
descripcionFormaPago TEXT);

CREATE TABLE PROFFLINE_TB_FORMA_PAGO_COBRANZA
(idFormaPago TEXT,
descripcionFormaPago TEXT);

CREATE TABLE PROFFLINE_TB_BANCO_PROMESA
(idBancoPromesa TEXT,
descripcionBancoPromesa TEXT);

CREATE TABLE PROFFLINE_TB_CABECERA_REGISTRO_PAGO_CLIENTE
(idCabecera INTEGER PRIMARY KEY,
codigoCliente TEXT,
codigoVendedor TEXT,
importeEntrado TEXT,
importeAsignado TEXT,
importeSinAsignar TEXT,
partidasSeleccionadas TEXT,
fechaCreacion TEXT,
sincronizado TEXT,
cabeceraSecuencia TEXT);

CREATE TABLE PROFFLINE_TB_PAGO_RECIBIDO
(idPagoRecibido INTEGER PRIMARY KEY,
idCabecera INTEGER,
codigoCliente TEXT,
idFormaPago TEXT,
importe TEXT,
referencia TEXT,
numeroFactura TEXT,
nroCtaBcoCliente TEXT,
idBancoCliente TEXT,
fechaVencimiento TEXT,
fechaDocumento TEXT,
idBancoPromesa TEXT);

CREATE TABLE PROFFLINE_TB_PAGO_PARCIAL
(idPagoParcial INTEGER PRIMARY KEY,
idCabecera INTEGER,
idPadre INTEGER,
numeroDocumento TEXT,
pstngDate TEXT,
docDate TEXT,
entryDate TEXT,
vencimiento TEXT,
currency TEXT,
importePagoTotal TEXT,
refOrgUn TEXT,
referencia TEXT,
claseDocumento TEXT,
posicion TEXT,
postKey TEXT,
importePagoParcial TEXT,
importePago TEXT,
psskt TEXT,
invRef TEXT,
invItem TEXT,
isLeaf TEXT,
isExpanded TEXT,
isReadOnly TEXT,
indice TEXT,
displayColor TEXT,
fiscYear TEXT,
fisPeriod TEXT,
sgtxt TEXT,
isReadOnly2 TEXT,
dbCrInd TEXT,
verzn TEXT,
comentario TEXT,
activo TEXT,
hijo TEXT);

COMMIT;