# Futures

## Common futures

**Default settings of turing machine is:**

* Start state: `S`
* Accept state: `AC`
* Reject state: `RJ`
* Blank symbol: `_`

**Comments and blank lines:**

Empty lines or that start with `--` or `-|` will be ignored by the compiler.

**State description:**

```
NAME:
  AT_CHAR OPERATION [TO_STATE [PUT_CHAR]]
  ...
```

Where:

* `NAME` - sequence of latin letters and numbers, starts with a letter.
* `AT_CHAR` - sequence of symbols describing the list of symbols from which 
              this shift could be performed.
              Regex for `AT_CHAR` template: ``"([^\\s`]|`[^\\s]`)+"``
* `OPERATION` - type of shift of turing machine that is one of: 
  * `<` - move to left (back)
  * `>` - move to right (forward)
  * `^` - stop on place
* `TO_STATE` - name of a state in which machine will after a shift. 
               Could be omitted or replace with `.`, by default will be `NAME`.
* `PUT_CHAR` - symbol that will be put at current tape's turing machine of turing machine.
               Could be omitted, default value is symbol at the current cell of tape.
               
**Environments:**

**Creating**
If you want to use a simple names for states in a current local task, 
you could wrap all states of this task in  `env` block like: 
```
env NAME 
  <STATE_1>
  <STATE_2>
  ... 
!env
``` 

**Usage**

Now it's only add suffix `NAME` to names of wrapped names. So you could use it like common states like:
```
S:
  0 ^ NAME_S 

NAME_T: 
  _ ^ AC 
```
Remember that **all** names will be converted. For use names out of environment add prefix `$` before the name.
               
## Compiler futures

**Change default turing machine settings:**

 For changing settings use `@<name of directive>: <value>` construct.
 
 Directives:
 * `start` or  `s` - name of the start state.
 * `accept` or `ac` - name of the accept state.
 * `reject` or `rj` - name of the reject state.
 * `blank` or `_` - one symbol that is blank.
 
 **User templates:**
 
 **Adding**
 
 For add some template in program use `@: <name of template> <list of symbols>` construct.
 The name of template should be a one single symbol.  
 
 **Usage**
 
 * For use a template add its name in atChar like: `` `N` ^ ...``, where N is a name of template. 
 * The name is replaced with a template list. 
 * Several names in a row could be combined into one brackets: `` `NMW` ^ ... ``.
 * It could be used with common symbols like: `` _`N`* ^ ... ``
 