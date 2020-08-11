#include <iostream>
#include <stdlib.h>

using namespace std;

/* run this program using the console pauser or add your own getch, system("pause") or input loop */
struct node
{
	int x;
	node*next;
	node*prev;
};


node* InsertionSort(node* r, int x)
{
	if(r == NULL)
	{
		r = (node*)malloc(sizeof(node));
		r->x = x;
		return r;
	}
	else if(r->x > x)
	{
		node* temp = (node*)malloc(sizeof(node));
		r->prev = temp;
		temp->next = r;
		temp->prev = NULL;
		r=temp;
		return temp;
	}
	else
	{
		node* temp = (node*)malloc(sizeof(node));
		node* iter = r;
		while(iter->next->x > x && iter->next != NULL)
		{
			iter = iter->next;
		}
		temp = iter->next;
		if(iter->next == NULL)
		{
			temp->x = x;
			temp->prev = iter->prev;
			temp->next = NULL;
			return r;
		}
		else
		{
			temp->x = x;
			temp->next = iter->next;
			temp->next->prev = temp;
			temp->prev = iter->prev;
			return r;
		}
	}

}
node * InsertionPop(node* r, int x)
{
	node* temp;
	node*iter = r;
	if(r->x == x)
	{
		temp =r;
		r=r->next;
		free(temp);
		cout << x << " deleted." << endl;
		return r;
	}
	else
	{
		while(iter->x != x)
		{
			iter = iter->next;
			if(iter->next == NULL)
			{
				iter->prev->next = NULL;
				cout << "Error, " << x <<  " couldn't found." << endl;
				return r;
			}
			else
			{
				free(iter);
				cout << x << " deleted." << endl;
				temp=iter;
				iter->next->prev = iter->prev;
				iter->prev->next = iter->next;
				free(temp);
				return r;
			}
		}
	}
}

void show(node*r)
{
	node*iter = r;
	while(iter->next != NULL)
	{
		cout << iter->x << " ";
		iter = iter->next;
	}
	cout << endl;
}







int main(int argc, char** argv) {
	int x;
	node *r = NULL;
	
	
	
	r=InsertionSort(r, 10);
	r=InsertionSort(r, 20);
	r=InsertionSort(r, 16);
	r=InsertionSort(r, 40);
	r=InsertionSort(r, 13);
	r=InsertionSort(r, 32);
	show(r);
	r=InsertionPop(r, 32);
	show(r);
	r=InsertionPop(r, 55);
	r=InsertionPop(r, 10);
	show(r);
	
	
	return 0;
}
