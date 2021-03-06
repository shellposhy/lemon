package cn.com.lemon.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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

import cn.com.lemon.base.Strings;

/**
 * Sensitive word filter based on bloem algorithm
 * <p>
 * <blockquote>
 * 
 * <pre>
 * File file = new File("sensitivewords.txt");
 * List<String> words = new ArrayList<String>();
 * String content = "社会有正气，民族才会生生不息，国家才会兴旺发达。";
 * Set<String> result = BloomFilters.build().word().file(file).list(words).filter().segment(null).sensitive(content);
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
	private Segment segment;
	private final Double FPP = 0.00001;
	private final Set<String> STOP_WORDS = new TreeSet<String>();

	// initialize bloom filter
	private final BloomFilter<String> filter = BloomFilter.create(new Funnel<String>() {
		private static final long serialVersionUID = 1L;

		@Override
		public void funnel(String from, PrimitiveSink into) {
			into.putString(from, Charsets.UTF_8);
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
	 * Stop word file
	 * 
	 * @return this
	 */
	public BloomFilters word() {
		return word(new File(this.getClass().getResource("/").getPath() + "stopwords.txt"));
	}

	public BloomFilters word(File stopWordFile) {
		if (stopWordFile.exists() && stopWordFile.isFile()) {
			InputStreamReader read = null;
			BufferedReader bufferedReader = null;
			try {
				read = new InputStreamReader(new FileInputStream(stopWordFile), StandardCharsets.UTF_8);
				bufferedReader = new BufferedReader(read);
				for (String txt = null; (txt = bufferedReader.readLine()) != null;) {
					STOP_WORDS.add(txt);
				}
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
		}
		return this;
	}

	/**
	 * Sensitive word file
	 * 
	 * @return this
	 */
	public BloomFilters file(File file) {
		if (file.exists() && file.isFile()) {
			InputStreamReader read = null;
			BufferedReader bufferedReader = null;
			try {
				read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
				bufferedReader = new BufferedReader(read);
				for (String txt = null; (txt = bufferedReader.readLine()) != null;) {
					filter.put(txt);
				}
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
		}
		return this;
	}

	/**
	 * Sensitive word list
	 * 
	 * @return this
	 */
	public BloomFilters list(List<String> words) {
		if (null != words && words.size() > 0) {
			for (String word : words) {
				filter.put(word.trim());
			}
		}
		return this;
	}

	/**
	 * Bloom filter
	 * 
	 * @return this
	 */
	public BloomFilters filter() {
		this.bloomFilter = filter;
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
		String[] stopWords = Strings.toArray(STOP_WORDS);
		for (String word : words) {
			List<Term> termList = this.segment.seg(word);
			for (Term term : termList) {
				// Eliminate stop words
				if (!(Arrays.binarySearch(stopWords, term.word) > 0))
					if (bloomFilter.mightContain(term.word)) {
						result.add(term.word);
					}
			}
		}
		return result;
	}
}
