package brockstar17.sorting;

public class Mergesort
{
	private static int[] temp;

	public static int[] mergesort(int[] sort) {
		if (sort.length <= 1)
			return sort;

		temp = new int[sort.length];
		sort(sort, 0, sort.length - 1);

		return sort;
	}

	private static void sort(int[] sort, int from, int to) {
		if (to - from < 2) {
			if (to > from && sort[to] < sort[from]) {
				int t = sort[to];
				sort[to] = sort[from];
				sort[from] = t;
			}
		}
		else {
			int middle = (to + from) / 2;
			sort(sort, from, middle);
			sort(sort, middle + 1, to);

			merge(sort, from, middle, to);
		}
	}

	private static void merge(int[] sort, int from, int middle, int to) {
		int i = from, j = middle + 1, k = from;

		while (i <= middle && j <= to) {
			if (sort[i] < sort[j]) {
				temp[k] = sort[i];
				i++;
			}
			else {
				temp[k] = sort[j];
				j++;
			}
			k++;

		}

		while (i <= middle) {
			temp[k] = sort[i];
			i++;
			k++;
		}

		while (j <= to) {
			temp[k] = sort[j];
			j++;
			k++;
		}

		for (k = from; k <= to; k++) {
			sort[k] = temp[k];
		}
	}

}
