package HighLow;

import java.util.Arrays;

public class ArrayStack<E> implements StackInterface<E>
{
	private E[] stack;
	private int topIndex;
	private static final int defaultInitialCapacity = 25;
	
	private void ensureCapacity()
	{
		if(topIndex==stack.length-1){
			stack = Arrays.copyOf(stack, 2*stack.length);
		}
	}
	
	public ArrayStack()
	{
		this(defaultInitialCapacity);
	}
	
	public ArrayStack(int initialCapacity)
	{
		@SuppressWarnings("unchecked")
		E[] tempStack = (E[])new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1;
	}

	public int size()
	{
		int size = 0;
		
		for(int i=0;i<stack.length;i++){
			if(stack[i]!=null){
				size++;
			}
		}
		
		return size;
	}
	
	public boolean isEmpty()
	{
		return (size()==0);
	}
	
	public E peek()
	{
		E top = null;
		
		if(!isEmpty()){
			top = stack[topIndex];
		}
		
		return top;
	}
	
	public void push(E newEntry)
	{
		ensureCapacity();
		topIndex++;
		stack[topIndex] = newEntry;
	}

	public E pop()
	{
		E top = null;
		
		if(!isEmpty()){
			top = stack[topIndex];
			stack[topIndex] = null;
			topIndex--;
		}
		
		return top;
	}

	public void clear()
	{
		while(!isEmpty()){
			pop();
		}
	}
}
