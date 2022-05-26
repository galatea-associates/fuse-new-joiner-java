package org.galatea.starter.service;

import java.util.List;
import org.galatea.starter.domain.IexHistoricalPrice;
import org.galatea.starter.domain.IexLastTradedPrice;
import org.galatea.starter.domain.IexSymbol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A Feign Declarative REST Client to access endpoints from the Free and Open IEX API to get market
 * data. See https://iextrading.com/developer/docs/
 */
@FeignClient(name = "IEX", url = "${spring.rest.iexBasePath}")
public interface IexClient {

  /**
   * Get a list of all stocks supported by IEX. See https://iextrading.com/developer/docs/#symbols.
   * As of July 2019 this returns almost 9,000 symbols, so maybe don't call it in a loop.
   *
   * @return a list of all of the stock symbols supported by IEX.
   */
  @GetMapping("/ref-data/symbols")
  List<IexSymbol> getAllSymbols(@RequestParam("token") String token);

  /**
   * Get the last traded price for each stock symbol passed in. See https://iextrading.com/developer/docs/#last.
   *
   * @param symbols stock symbols to get last traded price for.
   * @return a list of the last traded price for each of the symbols passed in.
   */
  @GetMapping("/tops/last")
  List<IexLastTradedPrice> getLastTradedPriceForSymbols(@RequestParam("symbols") String[] symbols,
      @RequestParam("token") String token);

  /**
   * Get the historical price for each symbol at each date.
   *
   * @param symbol the symbol for which to get the prices.
   * @param date the date on which to get the prices.
   * @return a List of IexLastTradedPrice objects for the given symbols.
   */
  @GetMapping("/time-series/HISTORICAL_PRICES/{symbol}")
  List<IexHistoricalPrice> getHistoricalPricesForSymbols(
          @PathVariable("symbol") String symbol,
          @RequestParam("on") String date,
          @RequestParam("from") String from,
          @RequestParam("to") String to,
          @RequestParam("interval") String interval,
          @RequestParam("token") String token);

}
