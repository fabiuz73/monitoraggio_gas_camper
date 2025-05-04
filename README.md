# **Monitoraggio Gas Camper**

## **Descrizione del progetto**
Il progetto consiste nella creazione di un sistema di monitoraggio per gas tossici e monossido di carbonio (CO) all'interno di un camper. Il sistema utilizza un microcontrollore ESP32 per raccogliere i dati da sensori di gas, visualizzarli su un display TFT e trasmetterli a uno smartphone tramite Bluetooth. Verrà gestito lo stato della batteria tramite una batteria ricaricabile LiPo e sarà possibile accendere e spegnere il display TFT per ottimizzare il consumo energetico.

---

## **Hardware**

### **Componenti**
| Nome                                           | Descrizione                                                     |
| ---------------------------------------------- | --------------------------------------------------------------- |
| ✅ **ESP32 NodeMCU ESP32-WROOM-32**            | Microcontrollore con WiFi e Bluetooth integrati.               |
| ✅ **Display AZDelivery 1.77 pollici SPI TFT** | Mostra dati in tempo reale (128x160 pixel, ST7735).             |
| ✅ **Sensore MQ-135**                           | Rileva gas tossici.                                             |
| ✅ **Sensore MQ-7**                             | Rileva monossido di carbonio (CO).                              |
| ✅ **Buzzer**                                   | Genera allarmi sonori.                                          |
| ✅ **KY-016 FZ0455 Modulo LED RGB**            | Indica lo stato del sistema (normale, preallarme, allarme).     |
| ✅ **Batteria LiPo 3.7V 5000mAh (mod. 755590)** | Batteria ricaricabile ad alta capacità.                         |
| ✅ **TP4056 con protezione (DW01 + MOSFET)**    | Modulo di ricarica con micro-USB o USB-C, protegge la batteria. |
| ✅ **Step-down MP1584 (3.3V regolabile)**       | Per alimentare ESP32 e display a 3.3V.                          |
| ✅ **Step-up MT3608 (5V)**                      | Per alimentare i sensori MQ a 5V partendo dalla batteria.       |
| ✅ **Partitore resistivo (es. 100k + 100k)**    | Per leggere la tensione della batteria su pin analogico ESP32.  |

---

## **Schema hardware**

### **Collegamenti principali**
1. **Batteria LiPo 3.7V 5000mAh**:
   - `+` e `-` collegati ai terminali `BAT+` e `BAT-` del modulo TP4056.

2. **Modulo TP4056**:
   - `BAT+` e `BAT-`: Collegati alla batteria.
   - `OUT+` e `OUT-`: Alimentano il resto del circuito.
   - Porta micro-USB/USB-C: Permette la ricarica della batteria.

3. **Step-down MP1584 (3.3V regolabile)**:
   - Ingresso: `IN+` e `IN-` collegati a `OUT+` e `OUT-` del TP4056.
   - Uscita: Configurata a 3.3V e alimenta:
     - ESP32 (pin `VIN` o `3.3V`).
     - Display TFT ST7735.

4. **Step-up MT3608 (5V)**:
   - Ingresso: `IN+` e `IN-` collegati a `OUT+` e `OUT-` del TP4056.
   - Uscita: Configurata a 5V e alimenta:
     - Sensore MQ-135.
     - Sensore MQ-7.
     - LED RGB (se necessario).

5. **Partitore resistivo (100k + 100k)**:
   - Resistenza superiore (`R1`, 100k): Collegata a `OUT+` del TP4056.
   - Resistenza inferiore (`R2`, 100k): Collegata a GND.
   - Nodo tra `R1` e `R2`: Collegato a un pin analogico dell'ESP32 (es. `GPIO34`).

6. **Gestione display TFT**:
   - Il pin `LEDA` del display TFT è collegato a un pin GPIO dell'ESP32 (es: `GPIO25`) tramite una resistenza da 330Ω.
   - Quando il GPIO è impostato su **HIGH**, la retroilluminazione del display è accesa.
   - Quando il GPIO è impostato su **LOW**, la retroilluminazione del display è spenta.

---

## **Software**

### **Arduino IDE**
- Gestisce i sensori MQ-135 e MQ-7 per rilevare i gas.
- Controlla il display TFT per visualizzare i dati in tempo reale e gestisce l'accensione/spegnimento tramite comandi Bluetooth.
- Implementa la logica di allarme (LED RGB e buzzer).
- Comunica via Bluetooth con l'app Flutter.
- Legge la tensione della batteria tramite il partitore resistivo e calcola la percentuale di carica.

### **Flutter**
- Sviluppa un'app Android per monitorare i dati in tempo reale.
- Configura le soglie dei sensori, calibra la batteria, gestisce gli allarmi e il controllo del display.
- Visualizza notifiche push e suona un MP3 in caso di allarme.
- Mostra grafici storici e lo stato della batteria.

---

## **Funzionalità principali**

### **1. ESP32**
- **Gestione sensori**:
  - Rileva i valori di gas tossici e monossido di carbonio.
  - Calcola i livelli di batteria e li trasmette all'app.
- **Gestione hardware**:
  - Controlla il LED RGB per indicare lo stato:
    - Verde: Normale.
    - Blu lampeggiante: Preallarme.
    - Rosso: Allarme.
  - Accende/spegne il display TFT per risparmiare energia.
  - Attiva il buzzer in caso di allarme.
- **Comunicazione Bluetooth**:
  - Invia i dati dei sensori e lo stato della batteria all'app.
  - Riceve configurazioni dall'app (soglie, calibrazione, gestione display, ecc.).

### **2. App Flutter**
#### **Menu principale**
- Visualizza i dati dei sensori in tempo reale con grafici a barre.
- Mostra il livello della batteria con un'icona realistica.
- Pulsanti principali:
  - **Visualizza valori in tempo reale**: Dati sensori e stato batteria.
  - **Imposta soglie sensori**: Configura i livelli per attivare gli allarmi.
  - **Calibrazione batteria**: Calibra l'offset della batteria e salva la configurazione.
  - **Stato batteria**: Mostra percentuale e tensione attuale.
  - **Visualizza grafico storico**: Valori massimi e minimi rilevati.
  - **Spegni display**: Accendi/spegni il display TFT per risparmiare energia.
  - **Connetti/Disconnetti Bluetooth**: Gestisce la connessione all'ESP32.

---

## **Prossimi Passaggi**
1. **Hardware**:
   - Realizzare il circuito con i componenti elencati.
   - Testare l'alimentazione, la gestione del display e la lettura della batteria.
2. **Software**:
   - Implementare la gestione del display e dei sensori nell'ESP32.
   - Creare l'app Flutter con le funzionalità descritte.
3. **Test**:
   - Verificare la comunicazione Bluetooth.
   - Testare gli allarmi e la gestione del display.
