package cn.com.lemon.framework.model;

/**
 * The tree data base bean
 * 
 * @author shellpo shih
 * @version 1.0
 */
public class Node<I, N> {
	public I id;
	public N name;

	public void setId(I id) {
		this.id = id;
	}

	public I getId() {
		return id;
	}

	public N getName() {
		return name;
	}

	public void setName(N name) {
		this.name = name;
	}

}
