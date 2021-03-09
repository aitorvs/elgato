Beeline
=======

Elgato is a command line tool to control your Elgato [Key Lights](https://www.elgato.com/en/gaming/key-light) and 
[Key Light Airs](https://www.elgato.com/en/gaming/key-light-air)

Usage
-----

```bash
$ elgato -h
Usage: elgato [OPTIONS] COMMAND [ARGS]...

Options:
  --version   Show the version and exit
  -h, --help  Show this message and exit

Commands:
  on        Turn ON the light
  off       Turn OFF the light
  info      Show key light info
  settings  Show the current light settings
  setup     Set-up the key light
```

## Install

**Mac OS**

```
$ brew install aitorvs/repo/elgato
```

**Other**

Download standalone JAR from
[latest release](https://github.com/aitorvs/elgato/releases/latest).
On MacOS and Linux you can `chmod +x` and execute the `.jar` directly.
On Windows use `java -jar`.

License
-------

    Copyright 2021 Aitor Viana

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
