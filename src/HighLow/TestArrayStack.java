package HighLow;

public class TestArrayStack
{
	public static void main(String[] args)
	{
		StackInterface<String> stack = new ArrayStack<String>();
		
		//testing for empty stack
		testSize(stack,0);
		testIsEmpty(stack,true);
		testPeek(stack,null);
		
		//testing for stack of one
		testPush(stack,"derek");
		testSize(stack,1);
		testIsEmpty(stack,false);
		testPeek(stack,"derek");
		testPop(stack,null);
		testIsEmpty(stack,true);
		
		//testing for stack of three
		String[] stackTest1 = {"alice","bob","charlie"};
		testPush(stack,stackTest1);
		testSize(stack,3);
		testIsEmpty(stack,false);
		testPeek(stack,stackTest1[stackTest1.length-1]);
		testPop(stack,stackTest1[stackTest1.length-2]);
		testIsEmpty(stack,false);
		
		//testing for stack bigger than default capacity
		String[] stackTest2 = {"test1","test2","test3","test4","test5",
				"test6","test7","test8","test9","test10","test11","test12",
				"test13","test14","test15","test16","test17","test18","test19",
				"test20","test21","test22","test23","test24","test25"};
		testPush(stack,stackTest2);
		testSize(stack,27);
		testPeek(stack,stackTest2[stackTest2.length-1]);
		
		testClear(stack);
		testPeek(stack,null);
		testIsEmpty(stack,true);
		testSize(stack,0);
	}
	
	private static void testSize(StackInterface<String> stack, int correct)
	{
		System.out.println("Testing size() with "+correct+" items:");
		
		System.out.print("size() found "+stack.size()+" items: ");
		if(stack.size()==correct){
			System.out.println("OK");
		}else{
			System.out.println("ERROR");
		}
		
		System.out.println();
	}
	
	private static void testIsEmpty(StackInterface<String> stack, boolean correct)
	{
		System.out.print("Testing isEmpty() with ");
		if(correct){
			System.out.println("an empty stack:");
		}else{
			System.out.println("a stack that is not empty:");
		}
		
		System.out.print("isEmpty() is "+stack.isEmpty()+": ");
		if(stack.isEmpty()==correct){
			System.out.println("OK");
		}else{
			System.out.println("ERROR");
		}
		
		System.out.println();
	}
	
	private static void testPeek(StackInterface<String> stack, String correct)
	{
		System.out.println("Testing peek() for a stack with "+correct+" at the top:");
		
		System.out.print("peek() found "+stack.peek()+" at the top: ");
		if(stack.peek()==correct){
			System.out.println("OK");
		}else{
			System.out.println("ERROR");
		}
		
		System.out.println();
	}
	
	private static void testPush(StackInterface<String> stack, String stackTest)
	{
		System.out.println("Testing push():");
		
		System.out.println("Pushing to stack: ");
		stack.push(stackTest);
		System.out.println("..."+stackTest+" pushed");
		
		
		System.out.println("New top of stack should be '"+stackTest+"'");
		System.out.print("Top of stack is "+stack.peek()+": ");
		if(stack.peek()==stackTest){
			System.out.println("OK");
		}else{
			System.out.println("ERROR");
		}
		
		System.out.println();
	}
	
	private static void testPush(StackInterface<String> stack, String[] stackTest)
	{
		System.out.println("Testing push():");
		
		System.out.println("Pushing to stack: ");
		for(int i=0;i<stackTest.length;i++){
			stack.push(stackTest[i]);
			System.out.println("..."+stackTest[i]+" pushed");
		}
		
		System.out.println("New top of stack should be '"+stackTest[stackTest.length-1]+"'");
		System.out.print("Top of stack is "+stack.peek()+": ");
		if(stack.peek()==stackTest[stackTest.length-1]){
			System.out.println("OK");
		}else{
			System.out.println("ERROR");
		}
		
		System.out.println();
	}
	
	private static void testPop(StackInterface<String> stack, String stackTest)
	{
		System.out.println("Testing pop():");
		
		String popped = stack.peek();
		System.out.println("Popping "+popped+" from stack: ");
		
		stack.pop();
		System.out.println("..."+popped+" popped");
		
		System.out.println("New top of stack should be '"+stackTest+"'");
		System.out.print("Top of stack is "+stack.peek()+": ");
		if(stack.peek()==stackTest){
			System.out.println("OK");
		}else{
			System.out.println("ERROR");
		}
		
		System.out.println();
	}
	
	private static void testClear(StackInterface<String> stack)
	{
		System.out.print("Testing clear(): ");
		
		stack.clear();
		
		if(stack.isEmpty()){
			System.out.println("OK");
		}else{
			System.out.println("ERROR");
		}
		
		System.out.println();
	}
}
