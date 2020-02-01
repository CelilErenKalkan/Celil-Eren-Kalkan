#include <iostream>

using namespace std;
/* run this program using the console pauser or add your own getch, system("pause") or input loop */
void push(int a[], int n, int x, int *top)
{
	if(*top == n-1)
	cout << "Error, Overflow" << endl;
	else
	*top += 1;
	a[*top] = x;
	cout << *top+1 << "th is Pushed: " << x << endl;
}

int pop(int a[], int *top)
{
	int ret;
	if(*top < 0)
	{
		cout << "Error, Underflow" << endl;
		return ret;
	}
	else
	ret = a[*top];
	*top -= 1;
	cout << ret << " is Popped." << endl;
	return ret;
}

void show(int a[], int n)
{
	for(int j=0; j<n; j++)
	{
		cout << a[j] << " ";
	}
	cout << endl;
}




int main(int argc, char** argv) {
	int a[5];
	int ret;
	int top = -1;
	
	ret = pop(a, &top);
	push(a, 5, 12, &top);
	push(a, 5, 21, &top);
	ret = pop (a, &top);
	push(a, 5, 22, &top);
	push(a, 5, 34, &top);
	push(a, 5, 48, &top);
	push(a, 5, 3, &top);
	show(a,5);
	ret = pop(a, &top);
	show(a,5);
	ret = pop(a, &top);
	show(a,5);	
	
	return 0;
}
