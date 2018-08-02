/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author cdi315
 Historique du fichier :
 Création : 28/06/2018, matin, par Florent :
     PAS d'attributs pour l'instant. Donc, pas de constructeur par défaut,
     getters, setters, ToString() par défaut.
 Historique des modifications du fichier :
 Florent, 28/06/2018 :
     1. Ajout de la fonction publique motDePasseEstValide.
     2. Ajout de la fonction publique nouveauMotDePasseEstFort.
 Florent, 29/06/2018 :
     1. Ajout de la fonction privée hacherUneChaine.
     2. Ajout des fonctions privées conversionCharArrayEnByteArray.
 Florent, 02/07/2018 :
     1. Ajout des attributs motDePasseSaisi, motDePasseCompl et mdpApresHachage.
     2. Ajout d'un constructeur par défaut.
     3. Ajout d'un constructeur : MotDePasseSQL(char[] motDePasseSaisi)
     4. Ajout des getteurs et setteurs.
 Florent, 03/07/2018 :
     1. Ajout d'un toString(), mais SANS l'affichage de motDePasseSaisi.
     2. Ajout de l'attribut motDePasseLu.
     3. Ajout d'un constructeur à 3 paramètres, à utiliser lorsque le mot de
        passe et le "grain de sel" sont connus et que l'on veut contrôler la
        validité d'un mot de passe saisi.
     4. Création de la fonction genererUnUUID.
     5. Désormais, dans hacherUneChaine, on assaisonne avec une pointe de sel.
     6. Modification de la signature (passage de deux à aucun paramètre) et
        du contenu de motDePasseEstValide.
 Florent, 04/07/2018 :
     1. Deux appels à afficherMessageErreur.
 Florent, 05/07/2018 :
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

public class MotDePasseSQL {
    private String motDePasseSaisi;
    private String motDePasseLu;
    //C'est le "grain de sel" associé au mot de passe :
    private String motDePasseCompl;
    private String mdpApresHachage;

    private Connection connexion;

    public MotDePasseSQL() {
    }

    //Contructeur à utiliser pour un changement de mot de passe (ou pour un
    //nouveau mot de passe) :
    public MotDePasseSQL(Connection connexion, String motDePasseSaisi)
            throws Exception {
        this.connexion = connexion;
        this.motDePasseSaisi = motDePasseSaisi;
        System.out.println("dbg MdpSQL.constr2args motDePasseSaisi = <<" +
                motDePasseSaisi + ">>.");
        //Générer un grain de sel associé au mot de passe.
        //C'est un UUID (soit 128 bits) :
        this.motDePasseCompl = genererUnUUID();
        System.out.println("dbg MdpSQL.constr2args NOUVEAU motDePasseCompl " +
                "= <<" + motDePasseCompl + ">>.");

        //Hacher le mot de passe en y mettant un grain de sel :
        this.mdpApresHachage = hacherUneChaineSQL(motDePasseSaisi,
                motDePasseCompl,"SHA2_256");
        System.out.println("dbg MdpSQL.constr2args mdpApresHachage = <<" +
                mdpApresHachage + ">>.");
    }
    
    /*
     * Contructeur à utiliser lorsque le mot de passe et le "grain de sel" sont
     * connus et que l'on veut contrôler la validité d'un mot de passe saisi.
     */
    public MotDePasseSQL(Connection connexion, String motDePasseSaisi,
            String motDePasseLu, String motDePasseCompl) throws Exception {
        this.connexion = connexion;
        this.motDePasseSaisi = motDePasseSaisi;
        System.out.println("dbg MdpSQL.constr4args motDePasseSaisi = <<" +
                motDePasseSaisi + ">>.");
        this.motDePasseLu = motDePasseLu;
        System.out.println("dbg MdpSQL.constr4args motDePasseLu = <<" +
                motDePasseLu + ">>.");
        this.motDePasseCompl = motDePasseCompl;
        System.out.println("dbg MdpSQL.constr4args motDePasseCompl = <<" +
                motDePasseCompl + ">>.");

        //Hacher le mot de passe en y mettant un grain de sel :
        this.mdpApresHachage = hacherUneChaineSQL(motDePasseSaisi,
                motDePasseCompl,"SHA2_256");
        System.out.println("dbg MdpSQL.constr4args mdpApresHachage = <<" +
                mdpApresHachage + ">>.");
    }
    
    public String getMotDePasseSaisi() {
        return motDePasseSaisi;
    }

    public void setMotDePasseSaisi(String motDePasseSaisi) {
        this.motDePasseSaisi = motDePasseSaisi;
    }

    public String getMotDePasseLu() {
        return motDePasseLu;
    }

    public void setMotDePasseLu(String motDePasseLu) {
        this.motDePasseLu = motDePasseLu;
    }
    
    //C'est le "grain de sel" associé au mot de passe :
    public String getMotDePasseCompl() {
        return motDePasseCompl;
    }

    //C'est le "grain de sel" associé au mot de passe :
    public void setMotDePasseCompl(String motDePasseCompl) {
        this.motDePasseCompl = motDePasseCompl;
    }

    public String getMdpApresHachage() {
        return mdpApresHachage;
    }

    public void setMdpApresHachage(String mdpApresHachage) {
        this.mdpApresHachage = mdpApresHachage;
    }

    @Override
    public String toString() {
        //ATTENTION : ne SURTOUT PAS afficher motDePasseSaisi !
        return "MotDePasse{" + "motDePasseLu=" + motDePasseLu +
                ", motDePasseCompl=" + motDePasseCompl +
                ", mdpApresHachage=" + mdpApresHachage + '}';
    }

    /*======================== Méthode(s) publique(s) ========================*/

    /*
     * Compare le mot de passe saisi avec le mot de passe stocké (pas en clair)
     * et renvoie true si le mot de passe est correct.
     */
    public boolean motDePasseEstValide() {
        System.out.println("dbg MdpSQL.motDePasseEstValide() IN :");
        System.out.println("dbg mdpApresHachage = <<" + mdpApresHachage + ">>.");
        System.out.println("dbg motDePasseLu = <<" + motDePasseLu + ">>.");
        System.out.println("dbg Sont-ils égaux ?");
        System.out.println("dbg MdpSQL.motDePasseEstValide() OUT :");
        return (mdpApresHachage.equals(motDePasseLu));
    }

    /*
     * Détermine si le nouveau mot de passe saisi est fort ou non.
     */
    public boolean nouveauMotDePasseEstFort(String motDePasseSaisi) {
        byte force = 0;
        if (motDePasseSaisi.length() >= 6) force++; //todo : 8 ? (M$ Windows); plus ? (SQL ? CNIL ?)
        //Au moins trois de ces catégories parmi quatre :
        //TODOBONUSTRACK : au moins une minuscule : boucle sur l'array sans le convertir en String ? / ou bien matches [a-z]+ ou qqch comme ça. / ou bien tri et recherche binaire ?
        //TODOBONUSTRACK : au moins une majuscule : boucle sur l'array sans le convertir en String ? / ou bien matches [A-Z]+ ou qqch comme ça. / ou bien tri et recherche binaire ?
        //TODOBONUSTRACK : au moins un chiffre : boucle sur l'array sans le convertir en String ? / ou bien matches [0-9]+ ou qqch comme ça. / ou bien tri et recherche binaire ?
        //TODOBONUSTRACK : au moins un signe de ponctuation : boucle sur l'array sans le convertir en String ? / ou bien matches PBTODO … / ou bien tri et recherche binaire ?
        //TODOBONUSTRACK : bien tester depuis la console, etc …
        return (force >= 1); //TODOBONUSTRACK 4
    }
    
    
    /*
     * Générer un UUID (Universally Unique IDentifier). C'est un identifiant
     * unique sur 128 bits.
     * Cette méthode est statique pour deux raisons :
     * - au moment de son appel dans le constructeur du mot de passe il y avait
     *   un avertissement "Overridable method call in constructor" (ce qui
     *   poserait problème si MotDePasseSQL avait une classe fille) ;
     * - pouvoir appeler cette méthode depuis la classe Employe sans devoir
     *   instancier un MotDePasseSQL juste pour ça.
     */
    public static String genererUnUUID() {
        System.out.println("dbg static MdpSQL.genererUnUUID() : un UUID va " +
                "être généré.");
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }    

    /*========================= Méthode(s) privée(s) =========================*/

/*todel @ end si finalement plus utilisée    
    / *
    Fonction utilitaire privée pour convertir un char[] en byte[] en utilisant
    le CharSet par défaut.
    * /
    private byte[] conversionCharArrayEnByteArray(char[] caracteres) {
        return conversionCharArrayEnByteArray(caracteres, Charset.defaultCharset());
    }

    / *
    Fonction utilitaire privée pour convertir un char[] en byte[] en utilisant
    un CharSet passé en paramètre.
    * /
    private byte[] conversionCharArrayEnByteArray(char[] caracteres, Charset charset) {
        //"Mapping" d'un CharBuffer sur le tableau char[] :
        CharBuffer tamponCaracteres = CharBuffer.wrap(caracteres);
        //Encodage du CharBuffer via le charset :
        ByteBuffer tamponOctets = charset.encode(tamponCaracteres);
        //Récupération du byte[] associé au ByteBuffer :
        byte[] tableauOctets = tamponOctets.array();
        //Copie de la zone qui nous intéresse
        // (ATTENTION : le buffer peut être plus grand !) :
        byte[] resultatOctets = Arrays.copyOf(tableauOctets,tamponOctets.limit());
        //Effacement du buffer du ByteBuffer :
        Arrays.fill(tableauOctets,(byte)0);
        //Retour du résultat :
        return resultatOctets;
    }    
todel.end
*/
    
    /* 
     * Fonction de "hachage" d'un mot par un algorithme passé en paramètre.
     * Valeurs possibles pour l'algorithme dans Microsoft SQL Server 2008 et
     * suivants :
     *     MD2           Non recommandé, car le mot de passe peut être "cassé".
     *     MD4           Non recommandé, car le mot de passe peut être "cassé".
     *     MD5           Non recommandé, car le mot de passe peut être "cassé".
                         C'est un algorithme sensible à la "collision".
     *     SHA           Non recommandé, car le mot de passe peut être "cassé".
                         C'est un algorithme sensible à la "collision".
     *     SHA1          Non recommandé, car le mot de passe peut être "cassé".
     *     SHA2_256      Algorithme de la famille SHA-2.
     *     SHA2_512      Algorithme de la famille SHA-2.
     * Extraits de "https://docs.microsoft.com/fr-fr/sql/t-sql/functions/
     *         hashbytes-transact-sql?view=sql-server-2017" :
     * À partir de SQL Server 2016 (13.x), tous les algorithmes autres que
     * SHA2_256 et SHA2_512 sont déconseillés. Des algorithmes plus anciens
     * (non recommandés) continueront de fonctionner, mais ils déclencheront
     * un événement de dépréciation.
     * La sortie se conforme à l'algorithme standard : 128 bits (16 octets)
     * pour MD2, MD4 et MD5 ; 160 bits (20 octets) pour SHA et SHA1 ; 256 bits
     * (32 octets) pour SHA2_256 et 512 bits (64 octets) pour SHA2_512.
     * Créateur : Florent.
     */
    //todo => choisir un algo, supprimer 'algo'
    private String hacherUneChaineSQL(String motDePasseSaisi, String grainDeSel, String algo) throws Exception {
        
        /*
         * Le futur résultat sous forme de chaîne hexadécimale, initialisé avec
         * une chaîne vide, juste au cas où …
         */
        String resultatChaineHexadecimale = "";
        
        try {
            //Récupérer le résultat du hachage : requête SQL
            String requeteHachage = "SELECT CONVERT(VARCHAR(255), "
                    + "HASHBYTES(?, CONVERT(VARCHAR(255),?,2)),2)";

            System.out.println("dbg hacherUneChaineSQL requête <<" +
                    requeteHachage + ">>.");
            
            PreparedStatement prepstmt = connexion.
                    prepareStatement(requeteHachage);

            prepstmt.setString(1, algo);
            //todoPB : motDePasseSaisi en Web : String en clair ????!!!!
            //todoPB : et, du coup, il transiterait en clair sur le WWW !!!!!???
            prepstmt.setString(2, motDePasseSaisi + grainDeSel);

            
            System.out.println("dbg hacherUneChaineSQL algo <<" +
                    algo + ">>.");
            System.out.println("dbg hacherUneChaineSQL motDePasseSaisi + " +
                    "grainDeSel <<" + motDePasseSaisi + grainDeSel + ">>.");
            
            
            ResultSet rsHachage = prepstmt.executeQuery();

            //Tentative de passer à l'enregistrement suivant :
            rsHachage.next();

            resultatChaineHexadecimale = rsHachage.getString(1);
            System.out.println("dbg hacherUneChaineSQL résultat du hachage <<" +
                    resultatChaineHexadecimale + ">>.");
            
            try {
                rsHachage.close();
            } catch(Exception ex) {
                throw new Exception("Fermeture du résultat lors du " +
                        "traitement du mot de passe :<br />" + ex.getMessage());
            }
            
            try {
                prepstmt.close();
            } catch(Exception ex) {
                throw new Exception("Fermeture de l'instruction lors du " +
                        "traitement du mot de passe :<br />" + ex.getMessage());
            }
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors du traitement du mot de " +
                    "passe :<br />" + sqlEx.getErrorCode() + " " +
                    sqlEx.getMessage());
        }

        try {
            connexion.close();
        } catch (SQLException sqlEx) {
            throw new Exception("Erreur SQL lors de la fermeture de la " +
                    "connexion servant à traiter le mot de passe :<br />" +
                    sqlEx.getErrorCode() + " " + sqlEx.getMessage());
        }
        
        return resultatChaineHexadecimale;

    }

    
    
    
    
}
