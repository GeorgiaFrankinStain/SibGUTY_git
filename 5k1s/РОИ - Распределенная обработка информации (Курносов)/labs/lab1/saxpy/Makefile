prog := saxpy
prog_objs := saxpy.o

CC := gcc
CFLAGS := -std=c99 -Wall -O2 -msse -mavx
LDFLAGS := -lm

.PHONY: all clean

all: $(prog)

$(prog): $(prog_objs)
	$(CC) -o $@ $^ $(LDFLAGS)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

saxpy.o: saxpy.c

clean:
	@rm -rf *.o $(prog)
