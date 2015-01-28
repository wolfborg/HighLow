package HighLow;

/**********************************************************
  * STACK INTERFACE
  *
 * @param <E> 
 *******************************************************/

public interface StackInterface<E> {
 /**
  * Return the number of elements in the stack.
  * @return number of elements in the stack. 
  */
  public int size();
  
 /** 
  * Return whether the stack is empty.
  * @return true if the stack is empty, false otherwise. 
  */
  public boolean isEmpty();
  
 /** 
  * Inspect the element at the top of the stack.
  * @return top element in the stack. 
  */
  public E peek();
  
 /**
  * Insert an element at the top of the stack.
  * @param element to be inserted.
  */
  public void push (E element);
  
 /** 
  * Remove the top element from the stack.
  * @return element removed.
  */
  public E pop();
  
 /**********************************************
   * My own added methods
 ***********************************************/
  
  /**
   * Pops the entire stack.
   */
  public void clear();
}