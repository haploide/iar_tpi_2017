
package logica;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import soporte.*;

//* @author 

public class LogicaResolucion {

    private javax.swing.JTextArea jTxtSolucion;
    
    public LogicaResolucion(javax.swing.JTextArea txt){
        jTxtSolucion=txt;
    }
    
    public void agregarTexto(String texto){
        jTxtSolucion.append(texto+"\n");
    }
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);  //crear un objeto Scanner
        int opcion=0;
        int [][] ConfInicial= {{1,2,3},{0,5,6},{4,7,8}};//new int[3][3]; //creamos un array bidimensional (3x3), corresponde a la Conf Inicial
        int [][] ConfFinal={{1,2,3},{4,5,6},{7,8,0}};//new int [3][3]; //creamos un array bidimensional, corresponde a la Conf Final
        
        int [][] ConfInicialAnterior= {{1,2,3},{0,5,6},{4,7,8}}; //iniciamos el array con valores predefinidos
        //luego de que se carguen valores en los anteriores, estos adquirirán dichos valores.
        int [][] ConfFinalAnterior={{1,2,3},{4,5,6},{7,8,0}};
        
        int fila=3, columna=3, profundidad=0, operacion=0, MAXProfundidad=0, MAXNodosExplorados=0 ;
        double SinLimites=0;
        Nodo solucion;
     
        System.out.println("************************************************");
        System.out.println("                   PUZZLE IUA                   ");
        System.out.println("************************************************");
        System.out.println("La Matriz del Puzzle será la siguiente");
        System.out.println("       [1er valor][2do valor][3er valor]   ");
        System.out.println("       [4to valor][5to valor][6to valor]   ");
        System.out.println("       [7mo valor][8vo valor][9no valor]   ");
        System.out.println("");
        System.out.println("Caracterisitcas del Juego:");
        System.out.println("-Solo se permite ingresar valores del 0-8");
        System.out.println("-Al valor 0 se lo considera como Espacio/Comodín");
        System.out.println("*************************************************");
        System.out.println("------------------------MENU-----------------------");
        System.out.println("1_ Ingresar valores al Juego, configuracion Inicial y Final(SOLUCION) ");
        System.out.println("2_ Utilizar la ultima configuracion Inicial y Final usada ");
        System.out.println("3_ Resolver con Primero en Anchura");
        System.out.println("4_ Resolver con Primero en Profundidad ");
        System.out.println("5_ Resolver con Primero el Mejor ");
        System.out.println("6_ Resolver con A* ");
        System.out.println("7_ Resolver con Escalada con Máxima Pendiente ");
        System.out.println("Salir (0)");
        opcion=sc.nextInt();

        while(opcion!=0){
            switch (opcion)
                {
                case 1:
                    //ANTES DE INGRESAR NUEVOS VALORES A LA MATRIZ, GUARDAMOS LA CONFIG ANTERIOR.
                    for (int i = 0; i < fila; i++) {
                        for (int j = 0; j < columna; j++) {
                            ConfInicialAnterior[i][j]=ConfInicial[i][j];
                        }
                    }
                    for (int i = 0; i < fila; i++) {
                        for (int j = 0; j < columna; j++) {
                            ConfFinalAnterior[i][j]=ConfFinal[i][j];
                        }
                    }
                    ///////////////////////////////////////////////////////////////////////
                    System.out.println("Ingrese los valores de la Configuracion Inicial:");
                    for (int i = 0; i < fila; i++) { //recorremos por fila, i=fila.
                     System.out.println("Fila "+i);
                        for (int j = 0; j < columna; j++) {// recorremos por columna, j=columna.
                            System.out.print("valor: ");
                            ConfInicial [i][j]=sc.nextInt();//asignamos el valor ingresado a dicha fila y columna.
                             //primero carga todas las columnas de la primer fila y despues pasa a la siguiente fila
                        }
                     System.out.println("");
                    }
                    System.out.println("Ingrese los valores de la Configuracion Final:");
                    for (int i = 0; i < fila; i++) {
                        System.out.println("Fila "+i);
                            for (int j = 0; j < columna; j++) {
                              System.out.print("valor: ");
                              ConfFinal [i][j]=sc.nextInt();                            
                            }
                        System.out.println("");
                    }
                    System.out.println("Matriz Inicial sera:");
                    imprimirMatrices(ConfInicial);              
                    System.out.println("Matriz Final/Solucion sera:");
                    imprimirMatrices(ConfFinal);               
                    break;
                    
                case 2:
                    
                    System.out.println("Los ultimos valores utilizados son:");
                    System.out.println("Matriz Inicial:");                
                    for (int i = 0; i < fila; i++) {
                        for (int j = 0; j < columna; j++) {
                            System.out.print("["+ConfInicialAnterior[i][j]+"]");
                            ConfInicial[i][j]=ConfInicialAnterior[i][j];
                        }
                        System.out.println("");
                    }
                    System.out.println("Matriz Final/Solucion:");                   
                    for (int i = 0; i < fila; i++) {
                         for (int j = 0; j < columna; j++) {
                             System.out.print("["+ConfFinalAnterior[i][j]+"]");
                             ConfFinal[i][j]=ConfFinalAnterior[i][j];
                        }
                        System.out.println("");
                    }
                    break;
                    
                case 3:
                    //lo primero que necesitamos hacer para buscar la solucion es convertir el 
                    //array ConfInicial en un nodo el cual sera el nodo origen (el primero)          
                    Nodo inicial=new Nodo(ConfInicial); 
                    
                    System.out.println("Profundidad MAXIMA: ");
                    MAXProfundidad=sc.nextInt();
                    System.out.println("Cantidad MAXIMA de Nodos Explorar: ");
                    MAXNodosExplorados=sc.nextInt();                       
                    System.out.println("---------Paso Por Paso----------");
                    solucion=buscarSolucionAnchura(inicial, ConfFinal,MAXProfundidad,MAXNodosExplorados );
                        
                    if(solucion==null)
                        System.out.println("NO SE ENCONTRO SOLUCION DENTRO DE LOS LIMITES ESTABLECIDOS");
                        
                    else
                        {
                            System.out.println("--------EL CAMINO A LA SOLUCION ES------------ ");                        
                            while(solucion.getPadre()!=null)
                            {
                                imprimirSolucion(solucion.getEstado());
                                solucion=solucion.getPadre();                    
                            }
                            System.out.println("Nodo inicial:");
                            for (int i = 0; i < fila; i++) {
                                for (int j = 0; j < columna; j++) {
                                    System.out.print("["+ConfInicial[i][j]+"]");
                                }
                                System.out.println("");
                            }
                        }                                       
                    break;
                    
                case 4:
                    Nodo inicial2=new Nodo(ConfInicial); 
                    
                    System.out.println("Profundidad MAXIMA: ");
                    MAXProfundidad=sc.nextInt();
                    System.out.println("Cantidad MAXIMA de Nodos a Explorar: ");
                    MAXNodosExplorados=sc.nextInt();
                    System.out.println("---------Paso Por Paso----------");
                    solucion=buscarSolucionProfundidad(inicial2, ConfFinal,MAXProfundidad,MAXNodosExplorados );                    
                        
                    if(solucion==null)
                        System.out.println("NO SE ENCONTRO SOLUCION DENTRO DE LOS LIMITES ESTABLECIDOS");
                        
                    else
                    {
                        System.out.println("--------EL CAMINO A LA SOLUCION ES------------ ");                       
                        while(solucion.getPadre()!=null)
                        {
                            imprimirSolucion(solucion.getEstado());
                            solucion=solucion.getPadre();                    
                        }
                        System.out.println("Nodo inicial:");
                        for (int i = 0; i < fila; i++) {
                            for (int j = 0; j < columna; j++) {
                                System.out.print("["+ConfInicial[i][j]+"]");
                            }
                            System.out.println("");
                        }
                    }                                                                     
                    break;
                
                case 5:
                    Nodo inicial3=new Nodo(ConfInicial);
                    
                    System.out.println("Profundidad MAXIMA: ");
                    MAXProfundidad=sc.nextInt();
                    System.out.println("Cantidad MAXIMA de Nodos a Explorar: ");
                    MAXNodosExplorados=sc.nextInt();
                    System.out.println("---------Paso Por Paso----------");
                    solucion=buscarSolucionPrimeroElMejor(inicial3, ConfFinal,MAXProfundidad,MAXNodosExplorados );                    
                        
                    if(solucion==null)
                        System.out.println("NO SE ENCONTRO SOLUCION DENTRO DE LOS LIMITES ESTABLECIDOS");
                        
                    else
                    {
                        System.out.println("--------EL CAMINO A LA SOLUCION ES------------ ");                        
                        while(solucion.getPadre()!=null)
                        {
                            imprimirSolucion(solucion.getEstado());
                            solucion=solucion.getPadre();                    
                        }
                        System.out.println("Nodo inicial:");
                        for (int i = 0; i < fila; i++) {
                            for (int j = 0; j < columna; j++) {
                                System.out.print("["+ConfInicial[i][j]+"]");
                            }
                            System.out.println("");
                        }
                    }                                                                                          
                    break;
                    
                case 6:
                    Nodo inicial4=new Nodo(ConfInicial);
                    
                    System.out.println("Profundidad MAXIMA: ");
                    MAXProfundidad=sc.nextInt();
                    System.out.println("Cantidad MAXIMA de Nodos a Explorar: ");
                    MAXNodosExplorados=sc.nextInt();
                    System.out.println("---------Paso Por Paso----------");
                    solucion=buscarSolucionAestrella(inicial4, ConfFinal,MAXProfundidad,MAXNodosExplorados );                    
                        
                    if(solucion==null)
                        System.out.println("NO SE ENCONTRO SOLUCION DENTRO DE LOS LIMITES ESTABLECIDOS");
                        
                    else
                    {
                        System.out.println("--------EL CAMINO A LA SOLUCION ES------------ ");                       
                        while(solucion.getPadre()!=null)
                        {
                            imprimirSolucion(solucion.getEstado());
                            solucion=solucion.getPadre();                    
                        }
                        System.out.println("Nodo inicial:");
                        for (int i = 0; i < fila; i++) {
                            for (int j = 0; j < columna; j++) {
                                System.out.print("["+ConfInicial[i][j]+"]");
                            }
                            System.out.println("");
                        }
                    }                                               
                    break;
                
                case 7:
                    Nodo inicial5=new Nodo(ConfInicial);
                    
                    System.out.println("Profundidad MAXIMA: ");
                    MAXProfundidad=sc.nextInt();
                    System.out.println("Cantidad MAXIMA de Nodos a Explorar: ");
                    MAXNodosExplorados=sc.nextInt();
                    System.out.println("---------Paso Por Paso----------");
                    solucion=buscarSolucionEscaladaMaxPendiente(inicial5, ConfFinal,MAXProfundidad,MAXNodosExplorados );                    
                        
                    if(solucion==null)
                    {   System.out.println("NO SE ENCONTRO SOLUCION");
                        
                    }   
                    else
                    {
                        System.out.println("--------EL CAMINO A LA SOLUCION ES------------ ");                       
                        while(solucion.getPadre()!=null)
                        {
                            imprimirSolucion(solucion.getEstado());
                            solucion=solucion.getPadre();                    
                        }
                        System.out.println("Nodo inicial:");
                        for (int i = 0; i < fila; i++) {
                            for (int j = 0; j < columna; j++) {
                                System.out.print("["+ConfInicial[i][j]+"]");
                            }
                            System.out.println("");
                        }
                    }                
                    break;
                    
                default:
                    break;
        }
           
        System.out.println("------------------------MENU-----------------------");
        System.out.println("1_ Ingresar valores al Juego, configuracion Inicial y Final(SOLUCION) ");
        System.out.println("2_ Utilizar la ultima configuracion Inicial y Final usada ");
        System.out.println("3_ Resolver con Primero en Anchura");
        System.out.println("4_ Resolver con Primero en Profundidad ");
        System.out.println("5_ Resolver con Primero el Mejor ");
        System.out.println("6_ Resolver con A* ");
        System.out.println("7_ Resolver con Escalada con Máxima Pendiente ");
        System.out.println("Salir (0)");
        opcion=sc.nextInt();    
    }
            
   }
    // es un metodo static porque nos permite instanciar SIN crear un objeto de la clase
    public static Nodo buscarSolucionAnchura(Nodo inicial, int[][] ConfFinal,int MAXProfundidad, int MAXNodosExplorados){
    // el metodo va a necesitar que le pasemos un Nodo inicial y un array bidirecc. con la solucion
        ArrayList <Nodo> listaAbierta=new ArrayList<Nodo>();//guarda todos los nodos que se van a
        //ir explorando/expandiendo, y el primer nodo que tiene que ser explorado es el Nodo inicial       
        ArrayList<Nodo> listaCerrada= new ArrayList<Nodo>(); 
        int profundidad=0;//el nodo inicial se encuentra en la profundidad 0  
        inicial.setProfundidad(profundidad);
        listaAbierta.add(inicial);
        int cantidadDeNodosExplorados=0;
             
        int prof=0;
        Nodo revisar=listaAbierta.remove(0);//asigna el nodo inicial al nodo que vamos a revisar
        double ramificacionMedio=0;
        
        while(cantidadDeNodosExplorados<MAXNodosExplorados&&profundidad<=MAXProfundidad){           
           // Nodo revisar=nodosExplorados.remove(0);//lo que hacemos es ir sacando del ArrayList a cada nodo para despues poder compararlo con la ConfFinal 
           //el nodo que vamos a sacar es el que esta en la primera posicion, por ende se va a comportar como una COLA (FIFO)
            cantidadDeNodosExplorados++;
            imprimirEstadoNodoRevisado(revisar,revisar.getEstado(),cantidadDeNodosExplorados);//el .getEstado va a convertir el nodo en un array bidir [][]
            int [] posicionDelCero=ubicarPosicionCero(revisar.getEstado());//posicion en el array[][] donde se ubica el cero
            //asi a partir de ello sabremos cuantos movimientos puede hacer el juego si el cero esta en el centro del array[][] entonces puede hacer 4 movimientos
            //arriba-abajo-derecha-izquierda, pero si esta en una punta solo dos movimientos            
            
            listaCerrada.add(revisar); 
            ArrayList<Nodo> hijos=new ArrayList<Nodo>();//creamos una lista de hijos que va a tener un nodo
            
            
            if(Arrays.deepEquals(revisar.getEstado(), ConfFinal)){//condicion de parada si el nodo que estamos revisando es igual a la solucion parar
                System.out.println("******* SOLUCION ENCONTRADA*********");
                System.out.println("Informacion:");
                System.out.println("> Cantidad de nodos Evaluados= "+listaCerrada.size());
                profundidad=calcularProfundidad(revisar);
                System.out.println("> Profundidad alcanzada: "+profundidad);
                ramificacionMedio=(double)cantidadDeNodosExplorados/(double)calcularProfundidad(revisar);
                System.out.println("> Ramificacion Medio= "+ramificacionMedio);            
                System.out.println("> Perfil de Ramificacion: ");               
                imprimirPerfilRamificacion(profundidad,listaCerrada );
                return revisar;//retorna el ultimo nodo explorado        
            }
            //A continuacion empezamos a crear los hijos del nodo
            if(posicionDelCero[0]!=0) //si la posicion del cero en las filas (posicion[0] es fila, pos[1]columna)
            //es diferente de cero va a poder crear un hijo con un movimiento hacia arriba o abajo(sube una fila o baja)             
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));//le estamos pasando un nodo igual al de antes pero ahora lo modificamos com los movimientos
                int arriba=hijo.getEstado()[posicionDelCero[0]-1][posicionDelCero[1]];//capturamos el valor que esta en la fila de arriba de esa columna en la variable arriba
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=arriba;//metemos en la posicion donde estaba el cero, el valor que estaba en la fila de arriba
                hijo.getEstado()[posicionDelCero[0]-1][posicionDelCero[1]]=0;//metemos en la fila de arriba el cero               
                if(!nodoRevisado(listaCerrada,hijo))//el metodo verifica si el hijo que estamos por expandir ya fue creado por otro nodo, si es asi no lo expandimos porque ya fue creado(ahorramos movimientos, no repetimos nodos)
                { 
                    hijo.setPadre(revisar);//creamos punteros a “revisar” en todos sus sucesores, de forma que pueda conocerse en todos ellos la identidad de su antecesor
                    //si no le asignamos un padre a cada hijo no vamos a poder usar el metodo imprimirPerfilRamificacion
                    listaAbierta.add(hijo);//metemos en el array de nodos explorados este nuevo nodo hijo para que luego se evalue
                    prof=calcularProfundidad(hijo);
                    hijo.setProfundidad(prof);
                }              
                hijos.add(hijo);//el hijo que creo lo guardo en la lista hijos
                
            }
            if(posicionDelCero[0]!=2)//como el puzzle es de 3x3 solo llega hasta la fila 2(0,1,2) entonces le decimos que si no es 2 que baje para crear el otro hijo                        
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));
                int abajo=hijo.getEstado()[posicionDelCero[0]+1][posicionDelCero[1]];//capturamos el valor que esta en la fila de abajo del cero
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=abajo;
                hijo.getEstado()[posicionDelCero[0]+1][posicionDelCero[1]]=0;               
                if(!nodoRevisado(listaCerrada,hijo))
                {
                    hijo.setPadre(revisar);
                    listaAbierta.add(hijo);
                    prof=calcularProfundidad(hijo);
                    hijo.setProfundidad(prof);
                }              
                 hijos.add(hijo);                
            }
               //Ahora cambiamos los valores en la misma fila donde esta ubicado el cero, osea movimiento a izq y derecha
            if(posicionDelCero[1]!=0) //no pasarme por izquierda                       
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));//le estamos pasando un nodo igual al de antes pero ahora lo modificamos com los movimientos
                int izquierda=hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]-1];//capturamos el valor que esta en la fila de arriba de esa columna en la variable arriba
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=izquierda;//metemos en la posicion donde estaba el cero, el valor que estaba en la fila de arriba
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]-1]=0;//metemos en la fila de arriba el cero
               
                if(!nodoRevisado(listaCerrada,hijo))
                {
                    hijo.setPadre(revisar);
                    listaAbierta.add(hijo);
                    prof=calcularProfundidad(hijo);
                    hijo.setProfundidad(prof);
                }//metemos en el array de nodos explorados este nuevo nodo hijo para que luego se evalue                                         
                hijos.add(hijo);                
            }           
            if(posicionDelCero[1]!=2)//no pasarme por derecha                       
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));
                int derecha=hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]+1];//capturamos el valor que esta en la fila de abajo del cero
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=derecha;
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]+1]=0;               
                if(!nodoRevisado(listaCerrada,hijo))
                {
                    hijo.setPadre(revisar);
                    listaAbierta.add(hijo);
                    prof=calcularProfundidad(hijo);
                    hijo.setProfundidad(prof);
                }                
                 hijos.add(hijo);               
            }
           revisar.setHijos(hijos);//le pasamos la lista de hijos a un nodo padre
           //usamos el Nodo revisar porque es el Padre de esos nodos hijos
            revisar=listaAbierta.remove(0);
            profundidad=calcularProfundidad(revisar);
            
        }
        return null;
    }
    
    public static void imprimirEstadoNodoRevisado(Nodo revisar, int[][] revis, int cantNodosRev)
    {
        int prof=0;
         while(revisar.getPadre()!=null)
                     {                         
                         revisar=revisar.getPadre();
                         prof++;  
                     }
        System.out.println("Siguiente Movimiento en la profundidad= "+prof);//nodo revisado
        System.out.println("Cantidad de nodos explorados= "+cantNodosRev);
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("["+revis[i][j]+"]");
            }
            System.out.println("");
        }
        
    }

    public static int[] ubicarPosicionCero(int[][] estado) {
        int[] posicionCero=new int[2];//array de tamaño 2, xq guarda la posicion "i","j"
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado.length; j++) {
                if (estado[i][j]==0) {
                    posicionCero[0]=i;
                    posicionCero[1]=j;
                }
            }           
        }       
        return posicionCero;
    }

    public static int[][] clonar(int[][] estado) {
      int[][] clon=new int[estado.length][estado.length];
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado.length; j++) {
               clon[i][j]=estado[i][j];
            }           
        }
        return clon;
    }

    public static boolean nodoRevisado(ArrayList<Nodo> listaCerrada, Nodo hijo) {
        
        for(Nodo revisado:listaCerrada){ //lo que hacemos es recorrer todo el arraylist "nodosYaRevisados" y comparamos con el siguinte if
          if(Arrays.deepEquals(revisado.getEstado(), hijo.getEstado()))  //si dentro de revisado existe algun nodo igual al nodo hijo que se esta comparando
          {
              return true;
          }                
        }
        return false;//por defecto retorna false (el nodo hijo no fue yarevisado anteriormente        
    }
   
    public static Nodo buscarSolucionProfundidad(Nodo inicial2, int[][] ConfFinal,int MAXProfundidad,int MAXNodosExplorados) {
        
        ArrayList <Nodo> listaAbierta=new ArrayList<Nodo>();
        ArrayList<Nodo> listaCerrada= new ArrayList<Nodo>();
        listaAbierta.add(inicial2);
        int cantidadDeNodosExplorados=0;
        int profundidad=0;
        double ramificacionMedio=0;      
        Nodo revisar=listaAbierta.remove(listaAbierta.size()-1);//con esto hacemos de cuenta que el ArrayList es una Pila(LIFO)
       
        while(cantidadDeNodosExplorados<MAXNodosExplorados&&profundidad<=MAXProfundidad)
        {   
            cantidadDeNodosExplorados++;
            imprimirEstadoNodoRevisado(revisar,revisar.getEstado(),cantidadDeNodosExplorados);
            int [] posicionDelCero=ubicarPosicionCero(revisar.getEstado());
                       
            listaCerrada.add(revisar); 
            ArrayList<Nodo> hijos=new ArrayList<Nodo>();
            
            if(Arrays.deepEquals(revisar.getEstado(), ConfFinal)){ 
                System.out.println("******* SOLUCION ENCONTRADA*********");
                System.out.println("Informacion:");
                System.out.println("> Cantidad de nodos Evaluados= "+listaCerrada.size()); 
                profundidad=calcularProfundidad(revisar);
                System.out.println("> Profundidad alcanzada: "+profundidad);
                ramificacionMedio=(double)cantidadDeNodosExplorados/(double)calcularProfundidad(revisar);
                System.out.println("> Ramificacion Medio= "+ramificacionMedio);            
                System.out.println("> Perfil de Ramificacion: ");               
                imprimirPerfilRamificacion(profundidad,listaCerrada );
                return revisar;
            }
            
             if(posicionDelCero[0]!=0)             
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado())); 
                int arriba=hijo.getEstado()[posicionDelCero[0]-1][posicionDelCero[1]];
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=arriba; 
                hijo.getEstado()[posicionDelCero[0]-1][posicionDelCero[1]]=0;                
                if(!nodoRevisado(listaCerrada,hijo)) 
                {
                    hijo.setPadre(revisar);
                    listaAbierta.add(hijo);
                    profundidad=calcularProfundidad(hijo);
                    hijo.setProfundidad(profundidad);
                }               
                hijos.add(hijo);
            }            
             if(posicionDelCero[1]!=0)                       
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));
                int izquierda=hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]-1]; 
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=izquierda;
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]-1]=0;
                if(!nodoRevisado(listaCerrada,hijo))
                {
                    hijo.setPadre(revisar);
                    listaAbierta.add(hijo);
                    profundidad=calcularProfundidad(hijo);
                    hijo.setProfundidad(profundidad);
                }                          
                 hijos.add(hijo);
            }           
             if(posicionDelCero[1]!=2)                  
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));
                int derecha=hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]+1];
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=derecha;
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]+1]=0;
                if(!nodoRevisado(listaCerrada,hijo))
                {
                    hijo.setPadre(revisar);
                    listaAbierta.add(hijo);
                    profundidad=calcularProfundidad(hijo);
                    hijo.setProfundidad(profundidad);
                }                  
                 hijos.add(hijo);
            }          
             if(posicionDelCero[0]!=2)                       
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));
                int abajo=hijo.getEstado()[posicionDelCero[0]+1][posicionDelCero[1]];//capturamos el valor que esta en la fila de abajo del cero
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=abajo;
                hijo.getEstado()[posicionDelCero[0]+1][posicionDelCero[1]]=0;
                if(!nodoRevisado(listaCerrada,hijo))
                {
                    hijo.setPadre(revisar);
                    listaAbierta.add(hijo);
                    profundidad=calcularProfundidad(hijo);
                    hijo.setProfundidad(profundidad);
                }               
                 hijos.add(hijo);
            }
            
           
             
           revisar.setHijos(hijos);
           revisar=listaAbierta.remove(listaAbierta.size()-1);//sacamos el ultimo nodo de la lista, es decir la convertimos en una PILA(FIFO)
           profundidad=calcularProfundidad(revisar);
           
        }
        return null;
    }

    public static Nodo buscarSolucionPrimeroElMejor(Nodo inicial3, int[][] ConfFinal,int MAXProfundidad,int MAXNodosExplorados) {
         
        ArrayList <Nodo> listaAbierta=new ArrayList<Nodo>();
        ArrayList<Nodo> listaCerrada= new ArrayList<Nodo>();
        listaAbierta.add(inicial3);
        int cantidadDeNodosExplorados=0;
        int profundidad=0;
        int costo=0;
        int costoTotal=0;
        double ramificacionMedio=0;
        Nodo revisar=listaAbierta.remove(0);
        costo=calcularCosto(revisar.getEstado(), ConfFinal);
        revisar.setCostoTotal(costo);
        
       while(cantidadDeNodosExplorados<MAXNodosExplorados&&profundidad<=MAXProfundidad)
        {        
            cantidadDeNodosExplorados++;
            imprimirEstadoNodoPMejorCostos(revisar.getEstado(),revisar.getCostoTotal(),revisar,cantidadDeNodosExplorados);
            int [] posicionDelCero=ubicarPosicionCero(revisar.getEstado());           
            
            listaCerrada.add(revisar); 
            ArrayList<Nodo> hijos=new ArrayList<Nodo>();
            
            if(Arrays.deepEquals(revisar.getEstado(), ConfFinal)){ 
                System.out.println("******* SOLUCION ENCONTRADA*********");
                System.out.println("Informacion:");
                System.out.println("> Cantidad de nodos Evaluados= "+listaCerrada.size()); 
                profundidad=calcularProfundidad(revisar);
                System.out.println("> Profundidad alcanzada: "+profundidad);                
                ramificacionMedio=(double)cantidadDeNodosExplorados/(double)calcularProfundidad(revisar);
                System.out.println("> Ramificacion Medio= "+ramificacionMedio);            
                System.out.println("> Perfil de Ramificacion: ");               
                imprimirPerfilRamificacion(profundidad,listaCerrada );              
                return revisar;
            }           
             if(posicionDelCero[0]!=0)             
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado())); 
                int arriba=hijo.getEstado()[posicionDelCero[0]-1][posicionDelCero[1]];
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=arriba; 
                hijo.getEstado()[posicionDelCero[0]-1][posicionDelCero[1]]=0; 
               
                hijo.setPadre(revisar);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);                   
                costo=calcularCosto(hijo.getEstado(), ConfFinal);
                costoTotal=costo;
                hijo.setCostoTotal(costo);
                listaAbierta.add(hijo);
                 
                hijos.add(hijo);
            }
            if(posicionDelCero[0]!=2)                       
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));
                int abajo=hijo.getEstado()[posicionDelCero[0]+1][posicionDelCero[1]];//capturamos el valor que esta en la fila de abajo del cero
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=abajo;
                hijo.getEstado()[posicionDelCero[0]+1][posicionDelCero[1]]=0;
                
                hijo.setPadre(revisar);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);                   
                costo=calcularCosto(hijo.getEstado(), ConfFinal);
                costoTotal=costo;
                hijo.setCostoTotal(costo);
                listaAbierta.add(hijo);           
              
                hijos.add(hijo);
            }
              
            if(posicionDelCero[1]!=0)                       
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));//le estamos pasando un nodo igual al de antes pero ahora lo modificamos com los movimientos
                int izquierda=hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]-1];//capturamos el valor que esta en la fila de arriba de esa columna en la variable arriba
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=izquierda;//metemos en la posicion donde estaba el cero, el valor que estaba en la fila de arriba
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]-1]=0;//metemos en la fila de arriba el cero
           
                hijo.setPadre(revisar);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);                   
                costo=calcularCosto(hijo.getEstado(), ConfFinal);
                costoTotal=costo;
                hijo.setCostoTotal(costo);
                listaAbierta.add(hijo);         
             
                hijos.add(hijo);
            }
            
            if(posicionDelCero[1]!=2)                  
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));
                int derecha=hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]+1];//capturamos el valor que esta en la fila de abajo del cero
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=derecha;
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]+1]=0;
                
                hijo.setPadre(revisar);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);                   
                costo=calcularCosto(hijo.getEstado(), ConfFinal);
                costoTotal=costo;
                hijo.setCostoTotal(costo);
                listaAbierta.add(hijo);
                
                hijos.add(hijo);
            }
           revisar.setHijos(hijos);
           
           Collections.sort(listaAbierta);//Ordena el ArrayList de acuerdo al "COSTO", asi el nodo hijo con menor costo se ubica primero para salir
           revisar=listaAbierta.remove(0);
           profundidad=calcularProfundidad(revisar);
           
        }
        return null;
    }

    public static int calcularCosto(int[][] hijo, int[][] ConfFinal) {
        
        int c=0;
        for (int i = 0; i < ConfFinal.length; i++) {
            for (int j = 0; j < ConfFinal.length; j++) {
                if(hijo[i][j]!=ConfFinal[i][j])
                    c++;
            }
        }
        return c;
    }

    public static void imprimirEstadoNodoPMejorCostos(int[][] estado, int costoTotal, Nodo rev, int cantidadExplorados) {
        int prof=0;
         while(rev.getPadre()!=null)
                     {                         
                         rev=rev.getPadre();
                         prof++;  
                     }
        System.out.println("Siguiente Movimiento en la profundidad= "+prof+", costo= "+costoTotal);
        System.out.println("Cantidad de nodos explorados= "+cantidadExplorados);
     
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado.length; j++) {
                System.out.print("["+estado[i][j]+"]");
            }
            System.out.println("");
        }
    }

    public static void imprimirSolucion(int [][] sol) {
       System.out.println("Proximo  ");
        for (int i = 0; i < sol.length; i++) {
            for (int j = 0; j<sol.length; j++) {
                System.out.print("["+sol[i][j]+"]");
            }
            System.out.println("");
        }
    }

    public static Nodo buscarSolucionAestrella(Nodo inicial4, int[][] ConfFinal,int MAXProfundidad,int MAXNodosExplorados) {
         
        ArrayList <Nodo> listaAbierta=new ArrayList<Nodo>();
        ArrayList<Nodo> listaCerrada= new ArrayList<Nodo>();
        listaAbierta.add(inicial4);
        int cantidadDeNodosExplorados=0;
        int profundidad=0;
        int profMax=0;
        int costo=0;
        int costoAestrella=0;
        double ramificacionMedio=0;
        
        Nodo revisar=listaAbierta.remove(0);
        costo=calcularCosto(revisar.getEstado(), ConfFinal);
        revisar.setCosto(costo);
        revisar.setProfundidad(profundidad);
        costoAestrella=costo+profundidad;
        revisar.setCostoTotal(costoAestrella);
        
        while(cantidadDeNodosExplorados<MAXNodosExplorados&&profMax<=MAXProfundidad)
        {      
            cantidadDeNodosExplorados++;
            imprimirEstadoNodoAestrellaCostos(revisar.getEstado(),revisar.getCosto(),revisar.getProfundidad(),revisar.getCostoTotal(),cantidadDeNodosExplorados);
            int [] posicionDelCero=ubicarPosicionCero(revisar.getEstado());
            
            listaCerrada.add(revisar); 
            ArrayList<Nodo> hijos=new ArrayList<Nodo>();
            
            if(Arrays.deepEquals(revisar.getEstado(), ConfFinal)){ 
                System.out.println("******* SOLUCION ENCONTRADA*********");
                System.out.println("Informacion:");
                System.out.println("> Cantidad de nodos Evaluados= "+listaCerrada.size()); 
                profundidad=calcularProfundidad(revisar);
                System.out.println("> Profundidad alcanzada: "+profundidad);
                ramificacionMedio=(double)cantidadDeNodosExplorados/(double)calcularProfundidad(revisar);
                System.out.println("> Ramificacion Medio= "+ramificacionMedio);            
                System.out.println("> Perfil de Ramificacion: ");
                
                imprimirPerfilRamificacion(profundidad,listaCerrada ); 
                return revisar;
            }
            
             if(posicionDelCero[0]!=0)             
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado())); 
                int arriba=hijo.getEstado()[posicionDelCero[0]-1][posicionDelCero[1]];
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=arriba; 
                hijo.getEstado()[posicionDelCero[0]-1][posicionDelCero[1]]=0; 
               
                hijo.setPadre(revisar);
                costo=calcularCosto(hijo.getEstado(), ConfFinal);
                hijo.setCosto(costo);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);
                costoAestrella=costo+profundidad;
                hijo.setCostoTotal(costoAestrella);   
                listaAbierta.add(hijo);   
                
                hijos.add(hijo);
            }
            if(posicionDelCero[0]!=2)                       
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));
                int abajo=hijo.getEstado()[posicionDelCero[0]+1][posicionDelCero[1]];//capturamos el valor que esta en la fila de abajo del cero
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=abajo;
                hijo.getEstado()[posicionDelCero[0]+1][posicionDelCero[1]]=0;
               
                hijo.setPadre(revisar);
                costo=calcularCosto(hijo.getEstado(), ConfFinal);
                hijo.setCosto(costo);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);
                costoAestrella=costo+profundidad;
                hijo.setCostoTotal(costoAestrella); 
                listaAbierta.add(hijo);                     
                
                hijos.add(hijo);
            }
              
            if(posicionDelCero[1]!=0)                       
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));//le estamos pasando un nodo igual al de antes pero ahora lo modificamos com los movimientos
                int izquierda=hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]-1];//capturamos el valor que esta en la fila de arriba de esa columna en la variable arriba
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=izquierda;//metemos en la posicion donde estaba el cero, el valor que estaba en la fila de arriba
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]-1]=0;//metemos en la fila de arriba el cero
               
                hijo.setPadre(revisar);
                costo=calcularCosto(hijo.getEstado(), ConfFinal);
                hijo.setCosto(costo);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);
                costoAestrella=costo+profundidad;
                hijo.setCostoTotal(costoAestrella);                  
                listaAbierta.add(hijo);                     
                
                hijos.add(hijo);
            }
            
            if(posicionDelCero[1]!=2)                  
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));
                int derecha=hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]+1];//capturamos el valor que esta en la fila de abajo del cero
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=derecha;
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]+1]=0;
                
                hijo.setPadre(revisar);
                costo=calcularCosto(hijo.getEstado(), ConfFinal);
                hijo.setCosto(costo);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);
                costoAestrella=costo+profundidad;
                hijo.setCostoTotal(costoAestrella);                   
                listaAbierta.add(hijo);                    
               
                hijos.add(hijo);
            }
           revisar.setHijos(hijos);
           
           Collections.sort(listaAbierta);//Ordena el ArrayList de acuerdo al "COSTO", asi el nodo hijo con menor costo se ubica primero para salir
           revisar=listaAbierta.remove(0);
           profMax=calcularProfundidad(revisar);          
        }
        return null;
    }

    public static void imprimirEstadoNodoAestrellaCostos(int[][] estado, int costo, Integer prof, Integer costoTotal, int cantidadExplorados) {
        
        System.out.println("Siguiente Movimiento");
        System.out.println("Costo= "+costo+",Profundidad= "+prof+",Costo Total= "+costoTotal);
        System.out.println("Cantidad nodos explorados= "+cantidadExplorados);
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado.length; j++) {
                System.out.print("["+estado[i][j]+"]");
            }
            System.out.println("");
        }
        
    }

    public static int calcularProfundidad(Nodo hijo) {
        int prof=0;
         while(hijo.getPadre()!=null)
                     {                         
                         hijo=hijo.getPadre();
                         prof++;  
                     }
         return prof;
    }

    public static void imprimirMatrices(int[][] ConfActual) {
        int valor=0;
        for (int i = 0; i < ConfActual.length; i++) {
            for (int j = 0; j < ConfActual.length; j++) {
                valor=ConfActual [i][j];
                System.out.print("["+valor+"]");               
            }
        System.out.println("");
        }
    }

    public static void imprimirPerfilRamificacion(int profundidad, ArrayList<Nodo> listaCerrada) {
        int prof=0;
        int cantNodosEnNivelDeProf;       
        for (int i = 0; i <=profundidad; i++)        
        {                
            cantNodosEnNivelDeProf=0;
            if(listaCerrada!=null)
            {
                for(Nodo n:listaCerrada)
                {
                    prof=n.getProfundidad();
                    if(prof==i)
                       cantNodosEnNivelDeProf++; 
                }
            }
            System.out.println("    Cantidad de nodos en el nivel: "+i+" es de: "+cantNodosEnNivelDeProf);
        }
    }

    public static Nodo buscarSolucionEscaladaMaxPendiente(Nodo inicial5, int[][] ConfFinal, int MAXProfundidad, int MAXNodosExplorados) {
     
        ArrayList <Nodo> listaAbierta=new ArrayList<Nodo>();
        ArrayList<Nodo> listaCerrada= new ArrayList<Nodo>();
        listaAbierta.add(inicial5);
        int cantidadDeNodosExplorados=0;
        int profundidad=0;
        int cantidadElementosBien=0;// cantidad de piezas bien colocadas
        double ramificacionMedio=0;
        Nodo revisar=listaAbierta.remove(0);
        cantidadElementosBien=calcularCantElemBien(revisar.getEstado(), ConfFinal);
        revisar.setCostoTotal(cantidadElementosBien);
        
       while(cantidadDeNodosExplorados<MAXNodosExplorados&&profundidad<=MAXProfundidad)
        {        
            cantidadDeNodosExplorados++;
            imprimirEstadoNodoEscMaxPend(revisar.getEstado(),revisar.getCostoTotal(),revisar,cantidadDeNodosExplorados);
            int [] posicionDelCero=ubicarPosicionCero(revisar.getEstado());           
            
            listaCerrada.add(revisar); 
            ArrayList<Nodo> hijos=new ArrayList<Nodo>();
            
            if(Arrays.deepEquals(revisar.getEstado(), ConfFinal)){ 
                System.out.println("******* SOLUCION ENCONTRADA*********");
                System.out.println("Informacion:");
                System.out.println("> Cantidad de nodos Evaluados= "+listaCerrada.size()); 
                profundidad=calcularProfundidad(revisar);
                System.out.println("> Profundidad alcanzada: "+profundidad);                
                ramificacionMedio=(double)cantidadDeNodosExplorados/(double)calcularProfundidad(revisar);
                System.out.println("> Ramificacion Medio= "+ramificacionMedio);            
                System.out.println("> Perfil de Ramificacion: ");               
                imprimirPerfilRamificacion(profundidad,listaCerrada );                
                return revisar;
            }           
             if(posicionDelCero[0]!=0)             
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado())); 
                int arriba=hijo.getEstado()[posicionDelCero[0]-1][posicionDelCero[1]];
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=arriba; 
                hijo.getEstado()[posicionDelCero[0]-1][posicionDelCero[1]]=0; 
                              
                hijo.setPadre(revisar);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);
                cantidadElementosBien=calcularCantElemBien(hijo.getEstado(), ConfFinal);                   
                hijo.setCostoTotal(cantidadElementosBien);
                listaAbierta.add(hijo);
                hijos.add(hijo);
            }
            if(posicionDelCero[0]!=2)                       
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));
                int abajo=hijo.getEstado()[posicionDelCero[0]+1][posicionDelCero[1]];//capturamos el valor que esta en la fila de abajo del cero
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=abajo;
                hijo.getEstado()[posicionDelCero[0]+1][posicionDelCero[1]]=0;
                 
                hijo.setPadre(revisar);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);                   
                cantidadElementosBien=calcularCantElemBien(hijo.getEstado(), ConfFinal);                   
                hijo.setCostoTotal(cantidadElementosBien);
                listaAbierta.add(hijo);                           
                hijos.add(hijo);
            }
              
            if(posicionDelCero[1]!=0)                       
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));//le estamos pasando un nodo igual al de antes pero ahora lo modificamos com los movimientos
                int izquierda=hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]-1];//capturamos el valor que esta en la fila de arriba de esa columna en la variable arriba
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=izquierda;//metemos en la posicion donde estaba el cero, el valor que estaba en la fila de arriba
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]-1]=0;//metemos en la fila de arriba el cero
                 
                hijo.setPadre(revisar);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);                   
                cantidadElementosBien=calcularCantElemBien(hijo.getEstado(), ConfFinal);                   
                hijo.setCostoTotal(cantidadElementosBien);
                listaAbierta.add(hijo);                         
                hijos.add(hijo);
            }
            
            if(posicionDelCero[1]!=2)                  
            {
                Nodo hijo=new Nodo(clonar(revisar.getEstado()));
                int derecha=hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]+1];//capturamos el valor que esta en la fila de abajo del cero
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]]=derecha;
                hijo.getEstado()[posicionDelCero[0]][posicionDelCero[1]+1]=0;
                
                hijo.setPadre(revisar);
                profundidad=calcularProfundidad(hijo);
                hijo.setProfundidad(profundidad);                   
                cantidadElementosBien=calcularCantElemBien(hijo.getEstado(), ConfFinal);                   
                hijo.setCostoTotal(cantidadElementosBien);
                listaAbierta.add(hijo);                
                hijos.add(hijo);
            }
           revisar.setHijos(hijos);
           
           Collections.sort(listaAbierta, Collections.reverseOrder());//Ordena el ArrayList de modo Ascendente
           Nodo mejorHijo=listaAbierta.remove(0);
            if (mejorHijo.getCostoTotal()<revisar.getCostoTotal()) {
                System.out.println("No existe un nodo mejor que el actual");
                break;
            }
           revisar=mejorHijo;
           profundidad=calcularProfundidad(revisar);
           listaAbierta.clear();//borro los hijos del nodo anterior
        }
        
        
     return null;    
    }

    public static int calcularCantElemBien(int[][] estado, int[][] ConfFinal) {
        
       int c=0;
        for (int i = 0; i < ConfFinal.length; i++) {
            for (int j = 0; j < ConfFinal.length; j++) {
                if(estado[i][j]==ConfFinal[i][j])
                    c++;
            }
        }
        return c; 
    }

    public static void imprimirEstadoNodoEscMaxPend(int[][] estado, Integer costoTotal, Nodo revisar, int cantidadDeNodosExplorados) {
         
        int prof=0;
         while(revisar.getPadre()!=null)
                     {                         
                         revisar=revisar.getPadre();
                         prof++;  
                     }
        System.out.println("Siguiente Movimiento en la profundidad= "+prof+", piezas bien colocadas= "+costoTotal);
        System.out.println("Cantidad de nodos explorados= "+cantidadDeNodosExplorados);
     
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado.length; j++) {
                System.out.print("["+estado[i][j]+"]");
            }
            System.out.println("");
        }
        
    }

    
   
  
}
