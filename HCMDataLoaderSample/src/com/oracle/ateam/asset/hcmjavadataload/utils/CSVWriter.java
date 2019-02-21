/* Copyright 2018, Oracle and/or its affiliates. All rights reserved.

The Universal Permissive License (UPL), Version 1.0
*/
package com.oracle.ateam.asset.hcmjavadataload.utils;

import com.oracle.ateam.asset.hcmjavadataload.client.HcmImport;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.regex.Pattern;


/**
 * Write CSV (Comma Separated Value) files.
 *
 * Fields are separated by commas, and enclosed in quotes if they contain commas
 * or quotes. Embedded quotes are doubled. Embedded spaces do not normally
 * require surrounding quotes. The last field on the line is not followed by a
 * comma. Null fields are represented by two commas in a row.
 *
 */
public class CSVWriter {
    private static final Logger LOGGER = Logger.getLogger(HcmImport.class.getName());
    public static final int DONT_ESCAPE_QUOTES = -1;
    public static final int MINIMAL_QUOTES = 0;
    public static final int SPACE_QUOTES = 1;
    public static final int ALL_QUOTES = 2;
    public static final int DEFAULT_QUOTE_LEVEL = SPACE_QUOTES;
    public static final char DEFAULT_QUOTE = '\"';
    public static final char DEFAULT_SEPARATOR = ',';
    public static final boolean DEFAULT_TRIM = true;
    public static final Pattern NEWLINE_REGEX = Pattern.compile("(\r\n|\r|\n|\n\r)");
    private PrintWriter pw; // PrintWriter where CSV fields will be written.
    private int quoteLevel; // how much extra quoting you want
    private char separator; // field separator character,
    private char quote; // quote character
    private final boolean trim; // true if write should trim lead/trail whitespace from fields before writing
    // them.
    private boolean wasPreviousField = false;
    // true if there has was a field previously written to this line, meaning there
    // is a comma pending to be written.
    private SimpleDateFormat dateFormat = null;
    private String nullString = "";
    private String newlineReplacement = null;
    private String separatorReplacement = null;
    private Pattern separatorRegEx = null;

    /**
     * Constructor
     *
     * @param pw
     *            Writer where fields will be written.
     * @param quoteLevel
     *            0 = minimal quotes 1 = quotes also around fields containing spaces
     *            2 = quotels around all fields. whether or not they contain commas,
     *            quotes or spaces.
     * @param separator
     *            field separator character, usually ',' in North America, ';' in
     *            Europe and sometimes '\t' for tab.
     * @param quote
     *            char to use to enclose fields containing a separator, usually '\"'
     * @param trim
     *            true if writer should trim leading/trailing whitespace (e.g.
     *            blank, cr, Lf, tab) before writing the field.
     */
    public CSVWriter(Writer pw, int quoteLevel, char separator, char quote, boolean trim) {
        if (pw instanceof PrintWriter) {
            this.pw = (PrintWriter) pw;
        } else {
            this.pw = new PrintWriter(pw);
        }
        this.quoteLevel = quoteLevel;
        this.separator = separator;
        this.quote = quote;
        this.trim = trim;
        this.newlineReplacement = null;
        this.separatorReplacement = null;
    }

    /**
     * convenience Constructor, defaults to quotelevel 1, comma separator , trim
     *
     * @param pw
     *            Writer where fields will be written.
     */
    public CSVWriter(Writer pw) {
        this(pw, DEFAULT_QUOTE_LEVEL, DEFAULT_SEPARATOR, DEFAULT_QUOTE, DEFAULT_TRIM);
    }

    /**
     * convenience Constructor, defaults to quotelevel 1, comma separator , trim
     *
     * @param pw
     *            Writer where fields will be written.
     */
    public CSVWriter(Writer pw, int quoteLevel) {
        this(pw, quoteLevel, DEFAULT_SEPARATOR, DEFAULT_QUOTE, DEFAULT_TRIM);
    }

    /**
     * Constructor
     *
     * @param path
     *            Full path of CSV file to be created
     * @param quoteLevel
     *            0 = minimal quotes 1 = quotes also around fields containing spaces
     *            2 = quotels around all fields. whether or not they contain commas,
     *            quotes or spaces.
     * @param separator
     *            field separator character, usually ',' in North America, ';' in
     *            Europe and sometimes '\t' for tab.
     * @param quote
     *            char to use to enclose fields containing a separator, usually '\"'
     * @param trim
     *            true if writer should trim leading/trailing whitespace (e.g.
     *            blank, cr, Lf, tab) before writing the field.
     */
    public CSVWriter(String path, int quoteLevel, char separator, char quote, boolean trim) throws IOException {
        pw = new PrintWriter(new FileWriter(path));
        this.quoteLevel = quoteLevel;
        this.separator = separator;
        this.quote = quote;
        this.trim = trim;
        this.newlineReplacement = null;
        this.separatorReplacement = null;
    }

    /**
     * Constructor
     *
     * @param path
     *            Full path of CSV file to be created
     */
    public CSVWriter(String path) throws IOException {
        this(path, DEFAULT_QUOTE_LEVEL, DEFAULT_SEPARATOR, DEFAULT_QUOTE, DEFAULT_TRIM);
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public void setSeparator(int sep) {
        int offset = '\u0000';
        this.separator = (char) (sep + offset);
    }

    /**
     * This string will be used when put(Object) is called with a null.
     *
     * @return the nullString (may be null)
     */
    public String getNullString() {
        return nullString;
    }

    /**
     * This string will be used when put(Object) is called with a null.
     *
     * @param nullString
     *            the nullString to set
     */
    public void setNullString(String nullString) {
        this.nullString = nullString;
    }

    /**
     * @return the dateFormat (may be null when not in use)
     */
    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * May be used by put(Object) to format a date.
     *
     * @param dateFormat
     *            the dateFormat to set
     */
    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * String to replace newlines in cells with or null if newlines should be kept
     *
     * @return
     */
    public String getNewlineReplacement() {
        return newlineReplacement;
    }

    /**
     * Set String to replace newlines in cells with or null if separators should be
     * kept (FIELD-267)
     *
     * @param newlineReplacement
     *            String to replace newlines with or null if separators should be
     *            kept
     */
    public void setNewlineReplacement(String newlineReplacement) {
        this.newlineReplacement = newlineReplacement;
    }

    /**
     * String to replace the separator in cells with or null if separator should be
     * kept
     *
     * @return
     */
    public String getSeparatorReplacement() {
        return separatorReplacement;
    }

    /**
     * Set String to replace the separator in cells with or null if separators
     * should be kept (FIELD-267)
     *
     * @param newlineReplacement
     *            String to replace the separator with or null if separators should
     *            be kept
     */
    public void setSeparatorReplacement(String separatorReplacement) {
        this.separatorReplacement = separatorReplacement;
        if (separatorReplacement != null) {
            String javaRegEx = (separator == '\t' ? "\\t" : String.valueOf(separator));
            separatorRegEx = Pattern.compile(javaRegEx);
        } else {
            separatorRegEx = null;
        }
    }

    /**
     * NOTE: If obj is a Collection this method will be called for each of it's
     * elements
     *
     * @param obj
     *            single object or Collection to be written.
     * @throws IllegalArgumentException
     */
    public void put(Object obj) {
        if (obj == null) {
            put(nullString);
        } else if (obj instanceof Collection) {
            for (Iterator it = ((Collection) obj).iterator(); it.hasNext();) {
                Object item = it.next();
                if (item instanceof Collection) {
                    for (Iterator it2 = ((Collection) item).iterator(); it2.hasNext();) {
                        put(it2.next());
                    }
                    nl();
                } else {
                    put(item);
                }
            }
        } else if (obj instanceof String) {
            put((String) obj);
        } else if (obj instanceof java.util.Date) {
            if (dateFormat == null) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            }
            put(dateFormat.format((java.util.Date) obj));
        } else {
            put(obj.toString());
        }
    }

    public void put(int s) {
        String str = Integer.toString(s);
        put(str);
    }

    /**
     * Write one csv field to the file, followed by a separator unless it is the
     * last field on the line. Lead and trailing blanks will be removed.
     *
     * @param s
     *            The string to write. Any additional quotes or embedded quotes will
     *            be provided by put. Null means start a new line.
     */
    public void put(String s) {
        if (pw == null) {
            throw new IllegalArgumentException("attempt to use a closed CSVWriter");
        }
        if (s == null) {
            s = nullString;
        }
        if (wasPreviousField) {
            pw.print(separator);
        }
        if (trim) {
            s = s.trim();
        }
        if (separatorReplacement != null) {
            // replace separators with the replacement text:
            s = separatorRegEx.matcher(s).replaceAll(separatorReplacement);
        }
        if (newlineReplacement != null) {
            // replace newlines with the replacement text:
            s = NEWLINE_REGEX.matcher(s).replaceAll(newlineReplacement);
        }
        if (quoteLevel == DONT_ESCAPE_QUOTES) {
            // FIELD-267: New quote level to not escape quotes at all
            // just print the string (with quotes or not):
            pw.print(s);
        } else if (s.indexOf(quote) >= 0) {
            /* worst case, needs surrounding quotes and internal quotes doubled */
            pw.print(quote);
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == quote) {
                    pw.print(quote);
                    pw.print(quote);
                } else {
                    pw.print(c);
                }
            }
            pw.print(quote);
        } else if (quoteLevel == ALL_QUOTES || quoteLevel == SPACE_QUOTES && s.indexOf(' ') >= 0 ||
                   s.indexOf(separator) >= 0) {
            /* need surrounding quotes */
            pw.print(quote);
            pw.print(s);
            pw.print(quote);
        } else {
            /* ordinary case, no surrounding quotes needed */
            pw.print(s);
        }
        /* make a note to print trailing comma later */
        wasPreviousField = true;
    }

    /**
     * Write one csv field to the file, followed by a separator unless it is the
     * last field on the line. Lead and trailing blanks will be removed. Quotes
     * inside the text will be replaced by EscapeCharacter
     *
     * @param s
     *            The string to write. Any additional quotes or embedded quotes will
     *            be provided by put. Null means start a new line.
     * @param quote
     *            In case you need the quote level defined field by field you can
     *            pass it in here. In this case, the put method ignores the set
     *            quoteLevel (For example: DPWN did specify the quotes on a "per
     *            field basis")
     * @param escapeQuote
     *            In case the string contains the quote, it will be escaped with the
     *            escapeQuote
     */
    public void put(String s, char quote, char escapeQuote) {
        if (pw == null) {
            throw new IllegalArgumentException("attempt to use a closed CSVWriter");
        }
        if (s == null) {
            s = "";
        }
        if (wasPreviousField) {
            pw.print(separator);
        }
        if (trim) {
            s = s.trim();
        }
        // check if the text contains a "quote" inside, which needs to be escaped:
        if (s.indexOf(quote) >= 0) {
            // worst case, needs surrounding quotes and internal quotes doubled
            pw.print(quote);
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == quote) {
                    pw.print(escapeQuote);
                } else {
                    pw.print(c);
                }
            }
            pw.print(quote);
        } else {
            // in case the quotes are explicitly set, they are ALWAYS used!
            pw.print(quote);
            pw.print(s);
            pw.print(quote);
        }
        /* make a note to print trailing comma later */
        wasPreviousField = true;
    }

    /**
     * Write a new line in the CVS output file to demark the end of record.
     */
    public void nl() {
        if (pw == null) {
            throw new IllegalArgumentException("attempt to use a closed CSVSWriter");
        }
        /* don't bother to write last pending comma on the line */
        pw.print("\r\n"); /* windows conventions since this is a windows format file */
        wasPreviousField = false;
    }

    /**
     * Close the PrintWriter.
     */
    public void close() {
        if (pw != null) {
            pw.close();
            pw = null;
        }
    }
}
