-module(game).
-export([start/2, kick/1, stop/1]).
-export([init/2]).


start(CountryName, OpponentName) ->
    spawn(?MODULE, init, [CountryName, OpponentName]),
    spawn(?MODULE, init, [OpponentName, CountryName]),
    ok.

init(CountryName, OpponentName) ->
    register(CountryName, self()),
    case whereis(OpponentName) of
      undefined ->
        io:format("Team ~s is ready. Waiting for ~s. ~n", [CountryName, OpponentName]),
        receive
          ready -> loop(CountryName,OpponentName)
        end;
      Pid ->
        link(Pid),
        Pid ! ready,
        io:format("Team ~s is ready. Starting match with ~s. ~n", [CountryName, OpponentName]),
        loop(CountryName, OpponentName)
    end.


kick(Player) ->
    whereis(Player) ! kick,
    ok.

stop(Player) ->
    whereis(Player) ! stop,
    ok.

loop(MyName, OppName) ->
    receive
      save ->
        io:format("Team ~s saved. ~n", [MyName]);

      score ->
        io:format("Team ~s scored! ~n", [MyName]);

      kick ->
        case random:uniform(2) of
          1 -> whereis(OppName) ! save;
          2 -> self() ! score
        end;

      stop -> exit(ok)

    % after 10000 -> ok
    end,

    loop(MyName,OppName).
