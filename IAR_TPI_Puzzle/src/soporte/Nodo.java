
package soporte;
import java.util.ArrayList;


public class Nodo implements Comparable<Nodo>{// se debe implementar la Interfaz Comparable para poder usar el metodo sort de la clase collections en objetos
    
    int [][] estado; //este atributo es la posicion en la que están los números del puzzle en 
    //ese nodo en ese momento. Por ello al igual que la ConfInicial y Final será un array
    //bidireccional [][]
    ArrayList<Nodo> hijos=new ArrayList<Nodo>(); //como un nodo puede tener uno o varios hijos, entonces se hace 
    //necesario guardarlos en un ArrayList de tipo Nodo, ya que guardará Nodos.
    Nodo padre;//cada Nodo tiene un padre
    Integer costo;// si no es del tipo Integer no podemos hacer el "compareTo" y ordenar los nodos
    Integer profundidad;
    Integer costoTotal;

    public Nodo(int[][] estado) {
        this.estado = estado;
        hijos=null;// al primer nodo se lo inicializa sin hijos y sin padre
        padre=null;
        costo=0;
        profundidad=0;
        costoTotal=0;
        
    }
    // generamos los getters y setters para poder insertar o sacar los datos
    public int[][] getEstado() {
        return estado;
    }

    public void setEstado(int[][] estado) {
        this.estado = estado;
    }

    public ArrayList<Nodo> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<Nodo> hijos) {
        this.hijos = hijos;
        if(hijos!=null)//el if se va a ejecutar si el ArrayList hijos no es vacio
        {
            for(Nodo hijo:hijos)//recorro todo el Arraylist, todos los hijos y le asignamos su padre
            {
                hijo.padre=this;//con el this llamamos al metodo que esta haciendo los hijos osea el metodo "setHijos"
            }
        }
    }

    public Nodo getPadre() {
        return padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public Integer getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Integer profundidad) {
        this.profundidad = profundidad;
    }

    public Integer getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Integer costoTotal) {
        this.costoTotal = costoTotal;
    }
    
    

    @Override
    public int compareTo(Nodo o) {//metodo de la interfaz comparable, que permite comparar y ordenar los objetos
        return costoTotal.compareTo(o.getCostoTotal());
    }
    
    
}
