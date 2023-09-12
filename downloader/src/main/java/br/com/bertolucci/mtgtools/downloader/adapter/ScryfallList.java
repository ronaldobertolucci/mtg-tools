package br.com.bertolucci.mtgtools.downloader.adapter;

import java.util.List;

public record ScryfallList<T>(
        boolean has_more,
        String next_page,
        List<T> data
) {
}
