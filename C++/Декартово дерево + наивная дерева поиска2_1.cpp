# include<bits/stdc++.h>
# define IoS ios_base::sync_with_stdio(0); cin.tie(NULL); cout.tie(NULL);
using namespace std;
long long y = 1e10;
struct item{
   long long  key , prior ;
   item *l , *r;
   item (long long ke, long long pri) : key(ke), prior(pri), l(NULL), r(NULL) {}
};
typedef item * pitem;
long long mx = 0 ;

///
struct SimpleTree{
    int x;
    SimpleTree *leftTree;
    SimpleTree *rightTree;
    SimpleTree(int x) : x(x), leftTree(NULL), rightTree(NULL) {}
};
typedef SimpleTree* TreeNode;

void add_node(TreeNode &tree, int x) {
    if (tree == NULL) tree = new SimpleTree(x);
    if (tree->x == x) return;
    if (tree->x < x) add_node(tree->rightTree, x);
    else add_node(tree->leftTree, x);
}

int count_deep(TreeNode tree){
    if(tree == NULL) return 0;
        return max(count_deep(tree->leftTree), count_deep(tree->rightTree)) + 1;
}

///
void split(pitem t , long long k , pitem &l , pitem &r){
     if(!t)
        l = r = NULL ;
     else {
        if(t->key < k)
            split(t->r , k , t->r , r) , l = t ;
        else split(t->l , k , l , t->l) , r = t;
     }
}
void inser(pitem &t , pitem it){

     if(!t) t = it ;
     else {
        if(it->prior > t->prior)
            split(t , it->key , it->l , it->r), t = it;
        else inser(t->key < it -> key ? t->r : t->l , it);
     }
}
void dfs(pitem t , int kol){
	if(kol > mx)
		mx = kol;
	if(t->l)
		dfs(t->l , kol + 1);
	if(t->r)
		dfs(t->r, kol + 1);

}
int main(){
    srand( time(0) );
    IoS;
   	long long  n , i , x , e = 0 ;
    char a;
    cin>>n;
    pitem t=NULL;
	TreeNode tree = NULL;
    for(i=1;i<=n;i++){
        cin >> x >> y;
		add_node(tree, x);
        inser(t , new item(x , y));
    }
    dfs(t , 0);
	cout << (count_deep(tree) - 1) - mx;
}
/*
3
*/
/*
6 7
1 1 2 1
2 1 2 2
2 2 3 2
3 2 3 3
3 3 1 2
1 2 2 5
2 5 3 6

*/
