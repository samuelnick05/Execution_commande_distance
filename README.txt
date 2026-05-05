Pour exécuter sur une machine distante, il faut changer une seule ligne dans le client :
Actuellement
javasocket = new Socket(InetAddress.getLocalHost(), 5000);
Ça se connecte à ta propre machine (localhost).
Pour une machine distante
javasocket = new Socket("192.168.X.X", 5000);
Remplace par l'adresse IP de la machine serveur.

Étapes pour tester
1. Trouver l'IP du serveur
Sur la machine serveur, ouvre cmd et tape :
ipconfig
Cherche "Adresse IPv4" — exemple : 192.168.1.15
2. Vérifier que les deux machines sont sur le même réseau
Les deux IP doivent commencer pareil — ex :
Serveur  : 192.168.1.15
Client   : 192.168.1.22  ✓ même réseau
3. Ouvrir le port 5000 sur le pare-feu du serveur
Sur la machine serveur :
Panneau de configuration → Pare-feu Windows → 
Règles entrantes → Nouvelle règle → Port 5000 TCP
4. Lancer dans l'ordre
1. Démarrer le Serveur sur la machine serveur
2. Démarrer le Client sur la machine cliente