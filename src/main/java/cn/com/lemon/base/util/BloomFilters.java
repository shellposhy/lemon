package cn.com.lemon.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.common.Term;

/**
 * Sensitive word filter based on bloem algorithm
 * <p>
 * <blockquote>
 * 
 * <pre>
 * File file = new File("sensitivewords.txt");;
 * String content = "社会有正气，民族才会生生不息，国家才会兴旺发达。";
 * Set<String> result = BloomFilters.build().file(file).filter().segment(null).sensitive(content);
 * </pre>
 * 
 * </blockquote>
 * <p>
 * 
 * @author shaobo shih
 * @version 1.0
 */
public class BloomFilters {
	private final static Logger LOG = LoggerFactory.getLogger(BloomFilters.class.getName());
	private BloomFilter<String> bloomFilter;
	private File file;
	private Segment segment;
	private final Double FPP = 0.00001;

	// initialize bloom filter
	private final BloomFilter<String> filter = BloomFilter.create(new Funnel<String>() {
		private static final long serialVersionUID = 1L;

		@Override
		public void funnel(String arg0, PrimitiveSink arg1) {
			arg1.putString(arg0, Charsets.UTF_8);
		}
	}, 1024 * 1024 * 32, FPP);

	// Bloom filter based on flunt style
	/**
	 * Instantiate the class
	 * 
	 * @return this
	 */
	public static BloomFilters build() {
		return new BloomFilters();
	}

	/**
	 * Sensitive word file
	 * 
	 * @return this
	 */
	public BloomFilters file(File file) {
		this.file = file;
		return this;
	}

	/**
	 * Bloom filter
	 * 
	 * @return this
	 */
	public BloomFilters filter() {
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try {
			read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
			bufferedReader = new BufferedReader(read);
			for (String txt = null; (txt = bufferedReader.readLine()) != null;) {
				filter.put(txt);
			}
			this.bloomFilter = filter;
		} catch (FileNotFoundException e) {
			LOG.error("The File " + file.getPath() + " not find!", e);
		} catch (IOException e) {
			LOG.error("The File I/O error occurs!", e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					LOG.error("BufferedReader close error occurs!", e);
				}
			}
			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
					LOG.error("InputStreamReader close error occurs!", e);
				}
			}
		}
		return this;
	}

	/**
	 * Chinese word splitter
	 * 
	 * @return this
	 */
	public BloomFilters segment(Segment segment) {
		if (segment != null) {
			this.segment = segment;
		} else {
			// Shortest path splitter
			this.segment = new DijkstraSegment().enableCustomDictionary(false).enableAllNamedEntityRecognize(true);
		}
		return this;
	}

	/**
	 * Get the sensitive words of the input document
	 * 
	 * @param words
	 *            input content
	 * @return {@link List}
	 */
	public Set<String> sensitive(String... words) {
		if (words == null)
			return null;
		Set<String> result = new TreeSet<String>();
		for (String word : words) {
			List<Term> termList = this.segment.seg(word);
			for (Term term : termList) {
				if (bloomFilter.mightContain(term.word)) {
					result.add(term.word);
				}
			}
		}
		return result;
	}
}
