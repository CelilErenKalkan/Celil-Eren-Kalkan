#include <iostream>
#include<stdlib.h>


using namespace std;

/* run this program using the console pauser or add your own getch, system("pause") or input loop */
struct node{
	node*next;
	int x;
};



node* push(node*r,int x){
		if(r == NULL)
		{
			r = (node*)malloc(sizeof(node));
			r->next=NULL;
			r->x = x;
			return r;
		}
			node*iter = r;
		while(iter->next !=NULL)
		{
			iter = iter->next;
		}
		iter->next= (node*)malloc(sizeof(node));
		iter = iter->next;
		iter->x = x;
		iter->next = NULL;
		return r;
	}

node* pop(node*r)
{
	if(r == NULL)
	{
		return r;
	}
	else if(r->next == NULL)
	{
		cout << "Deleted: " << r->x << endl;
		node*iter =r;
		r=NULL;
		return r;
	}
	else
	{
		node*iter = r;
		while(iter->next->next !=NULL)
		{
			iter = iter->next;
		}
		cout << "Deleted: " << iter->next->x << endl;
		iter->next =NULL;
		return r;
	}
}




void show(node*r)
{
	if(r==NULL)
	{
		cout << "Empty." << endl;
	}
	else
	{
		node*temp = r;
		while(temp != NULL)
		{
			cout << temp->x << endl;
			temp = temp->next;
		}
	}
}


int main(int argc, char** argv) {
	node*r= NULL;
	r=push(r,50);
	r=push(r,40);
	r=push(r,60);
	show(r);
	r=pop(r);
	r=pop(r);
	show(r);
	r=push(r,100);
	show(r);
	cout << r->x << endl;
	r=pop(r);
	show(r);
	
	
	return 0;
}
