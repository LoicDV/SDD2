public class T extends AVL<Segment> {
    
    // Construteurs
    public T(Segment head, AVL<Segment> left, AVL<Segment> right) {
        super(head, left, right);
    }

    public T() {
        super();
    }

    // Retourne true si le segment se trouve à gauche (si boolean left = true)
    // ou à droite (si boolean left = false) du segment en parametre.
    public boolean isLeftOrRight(Segment segment, double h, boolean left) {
        boolean verif = false;
        Segment head = this.getData();
        
        if (head.isVertical()) {
            if (segment.isVertical()) {
                if (left) {
                    if (head.getUpper().getX() <= segment.getUpper().getX()) {
                        verif = true;
                    }
                }
                else {
                    if (head.getUpper().getX() >= segment.getUpper().getX()) {
                        verif = true;
                    }
                }
            }
            else {
                double segment_pente = segment.getPente();
                double segment_param_p = segment.getParam_P();
                double segment_x = (h - segment_param_p) / segment_pente;
                double head_x = head.getUpper().getX();
                if (left) {
                    if (head_x <= segment_x) {
                        verif = true;
                    }
                }
                else {
                    if (head_x >= segment_x) {
                        verif = true;
                    }
                }
            }
        }
        
        else if (segment.isVertical()) {
            double head_pente = head.getPente();
            double head_param_p = head.getParam_P();
            double head_x = (h - head_param_p) / head_pente;
            double segment_x = segment.getUpper().getX();
            if (left) {
                if (head_x <= segment_x) {
                    verif = true;
                }
            }
            else {
                if (head_x >= segment_x) {
                    verif = true;
                }
            }
        }

        else {
            double head_pente = head.getPente();
            double segment_pente = segment.getPente();
            double head_param_p= head.getParam_P();
            double segment_param_p = segment.getParam_P();

            double head_x = (h - head_param_p) / head_pente;
            double segment_x = (h - segment_param_p) / segment_pente;

            if (left) {
                if (head_x <= segment_x) {
                    verif = true;
                }
            }
            else {
                if (head_x >= segment_x) {
                    verif = true;
                }
            }
        }
        return verif;
    }

    // Ajoute une donnée dans la struture T.
    public void insertT(Segment segment, double h) {
        if (this.isEmpty()) {
            this.insertTEmpty(segment);
        }
        else {
            if (this.isLeftOrRight(segment, h, true)) {
                if (this.getRightAVL().isEmpty()) {
                    this.getRightAVL().setData(segment);
                    this.getLeftAVL().setData(this.getData());
                    this.equilibrate();
                }
                else {
                    ((T) this.getRightAVL()).insertT(segment, h);
                    this.equilibrate();
                }
            }
            else {
                if (this.getLeftAVL().isEmpty()) {
                    this.getLeftAVL().setData(segment);
                    this.getRightAVL().setData(this.getData());
                    this.setData(segment);
                    this.equilibrate();
                }
                else {
                    ((T) this.getLeftAVL()).insertT(segment, h);
                    this.equilibrate();
                }
            }
        }
    }

    // Ajoute une donnée dans la struture T qui est vide.
    public void insertTEmpty(Segment segment) {
        this.setData(segment);
        this.setHeight(this.getHeight() + 1);
    }

    // Supprime segment de la structure T.
    public void suppressT(Segment segment) {
        
    }

    // Supprime la racine de T.
    public void suppressTRoot() {
        
    }

    // Supprime le minimum de T.
    public Segment suppressTMin() {
        
    }
}