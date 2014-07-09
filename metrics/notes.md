---
layout: default
title: Notes on Metric Tensors
---

Notes on Metric Tensors
=======================

*These are some notes starting after 
[Nicolas Behr's physics talk](https://www.wiki.ed.ac.uk/display/RBM/Session+5%3A+Differential+Geometry+III+and+Classical+Hamiltonian+mechanics+II)
at the School of Informatics on Tuesday 8th july 2014. It is very
hand-wavy but hopefully might give a useful insight into metric
tensors.*

Afterwards, I asked what the difference between an outer product and a
tensor product is, and wrote on the board something that looked like
high-school linear algebra

$$
\left( \begin{array}{c}
  1 \\
  2 \end{array} \right)
\left( \begin{array}{cc}
  3 & 4
\end{array} \right)
=
\left( \begin{array}{cc}
  3 & 4 \\
  6 & 8
\end{array} \right)
$$

or writing down the same thing in a slightly better way,

$$
\vec{u} \otimes \vec{v} = u_a v^b = w_a^b
$$

which Nicolas corrected to include the basis vectors, but basically
agreed an outer product like this, which is just all the different
ways we can multiply the components (in the forward direction) is
basically equivalent to the tensor product as long as we can keep
track of which elements go where.

$$
\vec{u} \cdot \vec{v} = \sum_a u_a v^a = \sum_a w_a^a = Tr(w)
$$

As usual if we follow Einstein's summation convention, we should
stop writing the big sigma because we quickly end up with too many of
them. But for the time being we'll keep them to make that obvious.

The indices are written a bit randomly. Why should they be up or down?
The answer here is that for the dot product it doesn't matter. The
trace of $w$ is invariant under (linear) coordinate transformations so
getting confused about which way the rows and columns go doesn't make
a difference.

Obviously it gets harder and harder to write down as the rank goes up
-- I have no idea how to write down a rank-4 tensor in matrix form
which is what you'd get doing the outer product of that with itself.

So then an inner product is what you get when you contract an outer
product where "contract" means taking the trace (for rank 2, it would
be something analogous but harder to visualise for higher rank).

With elementary linear algebra we also have a cross product, which is
where this stuff gets much harder to write down with rank > 2. But it
seems natural that we should be able to generalise the idea of cross
products to these things. It turns out that we need a new symbol to
help us do this, a permutation symbol named after Levi-Civita (who was
a mathematician that wrote one of the main early works in this area,
the somewhat starkly titled "The Absolute Differential Calculus").

Anyways, this symbol $\epsilon_{ijkl...}$ 1 if the indices are an even
permutation of (1,2,3,4...), -1 if it is odd, and zero if any are
repeated. With this thing, 

$$
  \vec{u} \times \vec{v} = \sum_j \sum_k \epsilon_{ijk} u^j v^k
$$

You can check that this works directly for the simple cases of, say,
three dimensional vectors in euclidean space that we are all familiar
with, and can just prove by induction that it works in higher
dimensions.

So far all of this is just a different way of writing down linear
algebra. Things get a bit hairy when we don't have a linear basis. If
we have spherical coordinates, trying to do dot or cross products by
multiplying r's and thetas together without paying attention clearly
won't work. We need a thing to tell us how to do that.

The important quantity that we want to preserve when we do these things
is distance. The length of a vector, which we get by using dot
products, should be the same no matter what coordinate system we use.
The thing that does this, that tells us how to multiply vectors
together in such a way that lengths are preserved, how much of each
coordinate to put into the result, is the metric tensor. It's called
"metric" for just this reason.

We know how to compute lengths in euclidean spaces, we know how to
(locally) transform from euclidean space to some sort of curvy space,
so we can work backwards. We can start by writing down our dot product
as

$$
\vec{u} \cdot \vec{v} = \sum_i \sum_j \delta_{ij} u^i v^j
$$

Nothing has really changed here, 
the delta is just our normal
Kronecker delta that is 1 if
\\(i = j\\)
and 0 otherwise. But what if it
wasn't? We know more about our metric tensor now. We know it fits in
the same place as the delta.
So let's go ahead and write down
something similar, where everything is expressed in some other
curvilinear coordinate system

$$
\vec{n} \cdot \vec{m} = \sum_i\sum_j g_{ij}n^im^j
$$

we want that they are the "same" vectors just represented with
different coordinates, so we can write,

$$
\begin{aligned}
  n^i &= \sum_j \frac{\partial y^i}{\partial x^j} u^j\\
  m^i &= \sum_j \frac{\partial y^i}{\partial x^j} v^j
\end{aligned}
$$

where $x^i$ are the old rectilinear coordinates and $y^i$ are
the new curvilinear ones. We want to make sure that

$$
\vec{u} \cdot \vec{v} = \vec{n} \cdot \vec{m}
$$

so,

$$
\sum_i\sum_j g_{ij}n^im^j =
\sum_i\sum_j\sum_k\sum_l g_{ij}
\frac{\partial y^i}{\partial x^k}u^k
\frac{\partial y^j}{\partial x^l}v^l
= \sum_i\sum_j \delta_{ij} u^iu^j
$$

this is a bit of a beast with lots of sums, but rearranging isn't that
hard, and we get that

$$
g_{ij} = \frac{\partial x^a}{\partial y^i}
         \frac{\partial x^b}{\partial y^j}
	 \delta_{ab}
$$

In a similar way we do cross products,

$$
\vec{n} \times \vec{m} =
\sum_i \sum_j \sum_k g_{ij} \epsilon_{jkl} u^k v^l
$$

The effect of applying a metric tensor this way ("applying" means
summing over its second index) is a kind of a distance preserving
coordinate transformation.

Whether we put the indices on the top or the bottom depends on whether
coordinate transformations happens in a covariant (top) or
contravariant (bottom) way. Normally vectors are covariant things. If
you make the vector bigger in some direction, you would expect its
representation in another coordinate system also to get bigger.

The metric tensor is the opposite. It's supposed to make lengths (dot
products) stay the same no matter how you transform. So if you go to a
coordinate system that stretches in the $x$ direction, assigns a
bigger number to that component, the metric tensor has to compensate
and assign a smaller number to the bit of the length sum that comes
from the corresponding components in the transformed vector. So we
call it contravariant.

A historical note is that this mathematics came to physics with
general relativity. Special relativity can be worked out with high
school algebra, but in general relativity, there was a distance
quantity that needed to be conserved under acceleration and there was
no way to make it work with euclidean coordinates. So the idea was
that it could be worked out with curvilinear coordinates and that this
metric tensor arranges so that the distances stay the same.

It's also hopefully clear why it's good to use the summation
convention and drop all of the annoying sigmas.
