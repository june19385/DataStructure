package Trees;

import Lists.Positions;

public abstract class AbstractTree<E> implements Tree<E> {

	@Override
	public boolean isInternal(Positions<E> p) throws IllegalArgumentException {
		return numChildren(p)>0;
	}

	@Override
	public boolean isExternal(Positions<E> p) throws IllegalArgumentException {
		return numChildren(p)==0;
	}

	@Override
	public boolean isRoot(Positions<E> p) throws IllegalArgumentException {
		return p==root();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int depth(Positions<E> p) {
		if(isRoot(p)) return 0;
		else return 1+depth(parent(p));
	}
	
	private int heightBad() {
		int h = 0;
		for(Positions<E> p : positions()) {
			if(isExternal(p)) {
				h = Math.max(h, depth(p));
			}
		}
		return h;
	}
	
	public int height(Positions<E> p) {
		int h=0;
		for(Positions<E> c : children(p)) {
			h = Math.max(h, 1+height(c));
		}
		return h;
	}
}
