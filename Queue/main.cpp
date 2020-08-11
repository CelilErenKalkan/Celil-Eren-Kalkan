#include <iostream>


using namespace std;

/* run this program using the console pauser or add your own getch, system("pause") or input loop */
void AddQ(int a[], int n, int x, int *head)
{
	if(*head >= n)
	cout << "Error, Overflow" << endl;
	else
	{
		a[*head] = x;
		cout << x << " is added." << endl;
		*head += 1;
	}
}


void OutQ(int a[], int *head, int *tail)
{
	int ret;
	if(*tail == *head)
	{
		cout << "Error, Underflow" << endl;
	}
	else
	{
		ret = a[*tail];
		cout << ret << " is deleted." << endl;
		for (int i = *tail+1; i < *head - *tail; i++)
		{
			a[i-1] = a[i];
		}
		*head -= 1;
	}
}

void show(int a[], int n)
{
	for(int j = 0; j < n; j++)
	{
		cout << a[j] << " ";
	}
	cout << endl;
}

int main(int argc, char** argv) {
	int a[4];
	int head = 0;
	int tail = 0;
	
	OutQ(a, &head, &tail);
	AddQ(a, 4, 10, &head);
	AddQ(a, 4, 12, &head);
	OutQ(a, &head, &tail);
	AddQ(a, 4, 22, &head);
	AddQ(a, 4, 67, &head);
	AddQ(a, 4, 35, &head);
	show(a, 4);
	OutQ(a, &head, &tail);
	AddQ(a, 4, 20, &head);
	AddQ(a, 4, 44, &head);
	
	
	
	
	
	
	
	
	
	return 0;
}
