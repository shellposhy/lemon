package cn.com.lemon.http;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class HtmlUtil {
	public static String subString(final String html, final int start, final int end) throws IOException {
		Reader r = new StringReader(html);
		ParserDelegator pd = new ParserDelegator();
		final Stack<String> strTagStick = new Stack<String>();
		pd.parse(r, new HTMLEditorKit.ParserCallback() {
			private int textCharCount = 0;
			private int lastStartPos = 0;
			private byte lastObjType = 0;
			private Stack<HTML.Tag> tagStick = new Stack<HTML.Tag>();
			private boolean dropTag = false;
			private int htmlLength = html.length();

			private void pushLastStrTag(int pos) {
				if ((!this.dropTag) && (this.lastObjType > 0) && (pos > this.lastStartPos)) {
					if (pos < this.htmlLength - 1) {
						strTagStick.push(html.substring(this.lastStartPos, pos));
					} else {
						strTagStick.push(html.substring(this.lastStartPos));
					}
				} else {
					this.dropTag = false;
				}
			}

			public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
				pushLastStrTag(pos);
				if (this.textCharCount < end) {
					this.tagStick.push(t);
				} else {
					this.dropTag = true;
				}
				this.lastObjType = 1;
				this.lastStartPos = pos;
			}

			public void handleEndTag(HTML.Tag t, int pos) {
				if (t.equals(this.tagStick.peek())) {
					if (this.lastObjType == 1) {
						this.dropTag = true;
					} else if ((this.textCharCount >= start) && (this.textCharCount < end)) {
						pushLastStrTag(pos);
					} else if (this.textCharCount < start) {
						strTagStick.pop();
						this.dropTag = true;
					} else if (this.textCharCount >= end) {
						pushLastStrTag(pos);
					}
					this.tagStick.pop();
				} else {
					this.dropTag = true;
				}
				this.lastObjType = 2;
				this.lastStartPos = pos;
			}

			public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
				pushLastStrTag(pos);
				if ((this.textCharCount < start) || (this.textCharCount >= end)) {
					this.dropTag = true;
				}
				this.lastObjType = 3;
				this.lastStartPos = pos;
			}

			public void handleText(char[] data, int pos) {
				pushLastStrTag(pos);
				for (char c : data) {
					if ((this.textCharCount >= start) && (this.textCharCount < end)) {
						strTagStick.push(String.valueOf(c));
					}
					this.textCharCount += 1;
				}
				this.lastObjType = 0;
				this.lastStartPos = pos;
			}
		}, false);
		r.close();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strTagStick.size(); i++) {
			sb.append((String) strTagStick.get(i));
		}
		if (((String) strTagStick.get(0)).startsWith("<html")) {
			sb.append("</html>");
		}
		if (sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

	public static String subByteString(final String html, final int start, final int end) throws IOException {
		Reader r = new StringReader(html);
		ParserDelegator pd = new ParserDelegator();
		final Stack<String> strTagStick = new Stack<String>();
		pd.parse(r, new HTMLEditorKit.ParserCallback() {
			private int textCharCount = 0;
			private int lastStartPos = 0;
			private byte lastObjType = 0;
			private Stack<HTML.Tag> tagStick = new Stack<HTML.Tag>();
			private boolean dropTag = false;
			private int htmlLength = html.length();

			private void pushLastStrTag(int pos) {
				if ((!this.dropTag) && (this.lastObjType > 0) && (pos > this.lastStartPos)) {
					if (pos < this.htmlLength - 1) {
						strTagStick.push(html.substring(this.lastStartPos, pos));
					} else {
						strTagStick.push(html.substring(this.lastStartPos));
					}
				} else {
					this.dropTag = false;
				}
			}

			public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
				pushLastStrTag(pos);
				if (this.textCharCount < end) {
					this.tagStick.push(t);
				} else {
					this.dropTag = true;
				}
				this.lastObjType = 1;
				this.lastStartPos = pos;
			}

			public void handleEndTag(HTML.Tag t, int pos) {
				if (t.equals(this.tagStick.peek())) {
					if (this.lastObjType == 1) {
						this.dropTag = true;
					} else if ((this.textCharCount >= start) && (this.textCharCount < end)) {
						pushLastStrTag(pos);
					} else if (this.textCharCount < start) {
						strTagStick.pop();
						this.dropTag = true;
					} else if (this.textCharCount >= end) {
						pushLastStrTag(pos);
					}
					this.tagStick.pop();
				} else {
					this.dropTag = true;
				}
				this.lastObjType = 2;
				this.lastStartPos = pos;
			}

			public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
				pushLastStrTag(pos);
				if ((this.textCharCount < start) || (this.textCharCount >= end)) {
					this.dropTag = true;
				}
				this.lastObjType = 3;
				this.lastStartPos = pos;
			}

			public void handleText(char[] data, int pos) {
				pushLastStrTag(pos);
				for (char c : data) {
					if ((this.textCharCount >= start) && (this.textCharCount < end)) {
						strTagStick.push(String.valueOf(c));
					}
					this.textCharCount = (this.textCharCount + (String.valueOf(c).getBytes().length >= 2 ? 2 : 1));
				}
				this.lastObjType = 0;
				this.lastStartPos = pos;
			}
		}, false);
		r.close();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strTagStick.size(); i++) {
			sb.append((String) strTagStick.get(i));
		}
		if (((String) strTagStick.get(0)).startsWith("<html")) {
			sb.append("</html>");
		}
		if (sb.length() > 0) {
			return sb.toString();
		}
		return null;
	}

	public static String getText(String html) throws IOException {
		ParserDelegator pd = new ParserDelegator();
		Reader r = new StringReader(html);
		final StringBuilder sb = new StringBuilder();
		pd.parse(r, new HTMLEditorKit.ParserCallback() {
			public void handleText(char[] data, int pos) {
				sb.append(data);
			}
		}, false);
		r.close();
		return sb.toString();
	}
}
